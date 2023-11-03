package kr.codesquad.jazzmeet.show.repository;

import static kr.codesquad.jazzmeet.show.entity.QShow.*;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

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

}
