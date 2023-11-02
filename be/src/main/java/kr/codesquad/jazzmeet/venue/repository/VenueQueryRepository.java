package kr.codesquad.jazzmeet.venue.repository;

import static kr.codesquad.jazzmeet.show.entity.QShow.*;
import static kr.codesquad.jazzmeet.venue.entity.QVenue.*;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import kr.codesquad.jazzmeet.venue.dto.ShowInfo;
import kr.codesquad.jazzmeet.venue.vo.NearbyVenue;
import kr.codesquad.jazzmeet.venue.vo.VenuePinsByWord;
import kr.codesquad.jazzmeet.venue.vo.VenueSearchData;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class VenueQueryRepository {

	private static final int NEARBY_VENUES_COUNT = 10;

	private final JPAQueryFactory query;

	public List<NearbyVenue> findNearbyVenuesByLocation(Double latitude, Double longitude) {
		return query.select(
				Projections.constructor(NearbyVenue.class,
					venue.id,
					venue.name,
					venue.roadNameAddress,
					venue.location,
					venue.thumbnailUrl
				)
			)
			.from(venue)
			.orderBy(
				Expressions.stringTemplate("ST_DISTANCE_SPHERE(venue.location, {0})",
						Expressions.stringTemplate("POINT({0}, {1})", longitude, latitude)
					)
					.asc()
			)
			.limit(NEARBY_VENUES_COUNT)
			.fetch();
	}

	public List<VenuePinsByWord> findVenuePinsByWord(String word) {
		List<VenuePinsByWord> result = query.select(
				Projections.constructor(VenuePinsByWord.class,
					venue.id,
					venue.name,
					venue.location)
			)
			.from(venue)
			.where(
				isContainWordInName(word).or(isContainWordInAddress(word))
			)
			.orderBy(venue.id.asc())
			.fetch();
		return result;
	}

	private BooleanExpression isContainWordInName(String word) {
		return venue.name.contains(word);
	}

	private BooleanExpression isContainWordInAddress(String word) {
		return venue.roadNameAddress.contains(word);
	}

	public List<VenueSearchData> searchVenueList(String word, PageRequest pageRequest, LocalDateTime todayStartTime,
		LocalDateTime todayEndTime) {
		return query.select(venue).from(venue)
			.leftJoin(show)
			.on(venue.id.eq(show.venue.id).and(show.startTime.between(todayStartTime, todayEndTime)))
			.where(venue.name.contains(word).or(venue.roadNameAddress.contains(word)))
			.limit(pageRequest.getPageSize())
			.offset(pageRequest.getOffset() - pageRequest.getPageSize())
			.transform(
				GroupBy.groupBy(venue.id).list(
					Projections.fields(VenueSearchData.class,
						venue.id,
						venue.thumbnailUrl,
						venue.name,
						venue.roadNameAddress.as("address"),
						venue.description,
						venue.location,
						GroupBy.list(
							Projections.fields(ShowInfo.class,
								show.startTime,
								show.endTime
							)).as("showInfo")
					)
				)
			);
	}

	public Long countSearchVenueList(String word) {
		return query.select(venue.count())
			.from(venue)
			.where(venue.name.contains(word).or(venue.roadNameAddress.contains(word)))
			.fetchFirst();
	}
}
