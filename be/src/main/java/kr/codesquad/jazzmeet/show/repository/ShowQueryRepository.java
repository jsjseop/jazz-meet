package kr.codesquad.jazzmeet.show.repository;

import static com.querydsl.core.group.GroupBy.*;
import static kr.codesquad.jazzmeet.show.entity.QShow.*;
import static kr.codesquad.jazzmeet.venue.entity.QVenue.*;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;

import kr.codesquad.jazzmeet.show.vo.ShowInfoByDate;
import kr.codesquad.jazzmeet.show.vo.ShowWithVenue;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class ShowQueryRepository {

	private final JPAQueryFactory query;

	public List<Integer> getExistShowsByMonth(Long venueId, LocalDate date) {
		return query.select(
				show.startTime.dayOfMonth()
			)
			.from(show)
			.where(show.venue.id.eq(venueId)
				.and(show.startTime.year().eq(date.getYear()))
				.and(show.startTime.month().eq(date.getMonthValue())))
			.fetch();
	}

	public List<Integer> getShowCalendar(LocalDate date) {
		return query.select(
				show.startTime.dayOfMonth()
			)
			.from(show)
			.where(show.startTime.year().eq(date.getYear())
				.and(show.startTime.month().eq(date.getMonthValue())))
			.groupBy(show.startTime.dayOfMonth())
			.fetch();
	}

	// 각 공연장에 대한 공연 조회
	public List<ShowWithVenue> getShowsByDate(LocalDate date) {
		return query
			.selectFrom(venue)
			.leftJoin(venue.shows, show)
			.where(isEqualLocalDate(date))
			.groupBy(venue.id, venue.name, show.id)
			.orderBy(venue.name.asc(), show.startTime.asc())
			.transform(groupBy(venue.id).list(
				Projections.constructor(ShowWithVenue.class,
					venue.id,
					venue.name,
					getAddressQuery(),
					list(
						Projections.constructor(ShowInfoByDate.class,
							show.id,
							show.poster.url,
							show.teamName,
							show.startTime,
							show.endTime))
				)
			));
	}

	private static BooleanExpression isEqualLocalDate(LocalDate date) {
		return show.startTime.year().eq(date.getYear())
			.and(show.startTime.month().eq(date.getMonthValue()))
			.and(show.startTime.dayOfMonth().eq(date.getDayOfMonth()));
	}

	private StringTemplate getAddressQuery() {
		return Expressions.stringTemplate(
			"CASE WHEN {0} LIKE '%시 %' THEN function('substring_index', {0}, ' ', 3) ELSE function('substring_index', {0}, ' ', 2) END",
			venue.roadNameAddress
		);
	}
}
