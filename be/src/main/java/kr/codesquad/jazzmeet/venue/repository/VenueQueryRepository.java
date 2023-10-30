package kr.codesquad.jazzmeet.venue.repository;

import static kr.codesquad.jazzmeet.venue.entity.QVenue.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import kr.codesquad.jazzmeet.venue.vo.NearbyVenue;
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
}
