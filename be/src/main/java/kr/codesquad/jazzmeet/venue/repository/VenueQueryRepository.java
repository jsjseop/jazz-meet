package kr.codesquad.jazzmeet.venue.repository;

import static com.querydsl.core.group.GroupBy.*;
import static kr.codesquad.jazzmeet.admin.entity.QAdmin.*;
import static kr.codesquad.jazzmeet.image.entity.QImage.*;
import static kr.codesquad.jazzmeet.review.entity.QReview.*;
import static kr.codesquad.jazzmeet.show.entity.QShow.*;
import static kr.codesquad.jazzmeet.venue.entity.QLink.*;
import static kr.codesquad.jazzmeet.venue.entity.QLinkType.*;
import static kr.codesquad.jazzmeet.venue.entity.QVenue.*;
import static kr.codesquad.jazzmeet.venue.entity.QVenueHour.*;
import static kr.codesquad.jazzmeet.venue.entity.QVenueImage.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import kr.codesquad.jazzmeet.image.entity.ImageStatus;
import kr.codesquad.jazzmeet.review.entity.ReviewStatus;
import kr.codesquad.jazzmeet.review.vo.ReviewDetail;
import kr.codesquad.jazzmeet.venue.dto.ShowInfo;
import kr.codesquad.jazzmeet.venue.dto.VenueInfo;
import kr.codesquad.jazzmeet.venue.vo.NearbyVenue;
import kr.codesquad.jazzmeet.venue.vo.VenueDetail;
import kr.codesquad.jazzmeet.venue.vo.VenueDetailImage;
import kr.codesquad.jazzmeet.venue.vo.VenueDetailLink;
import kr.codesquad.jazzmeet.venue.vo.VenueDetailVenueHour;
import kr.codesquad.jazzmeet.venue.vo.VenuePins;
import kr.codesquad.jazzmeet.venue.vo.VenueSearchData;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class VenueQueryRepository {

	private static final int NEARBY_VENUES_COUNT = 10;
	private static final String ADDRESS = "address";
	private static final String SHOWINFO = "showInfo";

	private final JPAQueryFactory query;

	public List<NearbyVenue> findNearbyVenuesByLocation(Point point) {
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
				Expressions.stringTemplate("ST_DISTANCE_SPHERE(venue.location, {0})", point)
					.asc()
			)
			.limit(NEARBY_VENUES_COUNT)
			.fetch();
	}

	public List<VenuePins> findVenuePinsByWord(String word) {
		return query.select(
				Projections.constructor(VenuePins.class,
					venue.id,
					venue.name,
					venue.location)
			)
			.from(venue)
			.where(containsInNameOrAddress(word))
			.orderBy(venue.id.asc())
			.fetch();
	}

	// 쿼리를 생성하여 사각형 범위 내에 있는 장소를 찾습니다.
	public List<VenuePins> findVenuePinsByLocation(Polygon range) {
		return query
			.select(Projections.constructor(VenuePins.class,
				venue.id,
				venue.name,
				venue.location))
			.from(venue)
			.where(isLocationWithInRange(range))
			.fetch();
	}

	public Page<VenueSearchData> findVenuesByLocation(Polygon range, Pageable pageable, LocalDate curDate) {
		List<VenueSearchData> venueSearchList = query.from(venue)
			.leftJoin(show)
			.on(venue.id.eq(show.venue.id))
			.on(isStartTimeEqCurDate(curDate))
			.where(isLocationWithInRange(range))
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.transform(
				groupBy(venue.id).list(
					Projections.fields(VenueSearchData.class,
						venue.id,
						venue.thumbnailUrl,
						venue.name,
						venue.roadNameAddress.as(ADDRESS),
						venue.description,
						venue.location,
						list(
							Projections.fields(ShowInfo.class,
								show.startTime,
								show.endTime
							)
						).as(SHOWINFO))
				)
			);

		JPAQuery<Long> venuesByLocationCount = getVenuesByLocationCount(range);

		return PageableExecutionUtils.getPage(venueSearchList, pageable, venuesByLocationCount::fetchOne);
	}

	private JPAQuery<Long> getVenuesByLocationCount(Polygon range) {
		return query.select(venue.count())
			.from(venue)
			.where(isLocationWithInRange(range));
	}

	private BooleanExpression isLocationWithInRange(Polygon range) {
		return Expressions.booleanTemplate("ST_Within({0}, {1})", venue.location, range);
	}

	public Page<VenueSearchData> searchVenueList(String word, Pageable pageable, LocalDate curDate) {
		List<VenueSearchData> venueSearchDataList = query.select(venue).from(venue)
			.leftJoin(show)
			.on(venue.id.eq(show.venue.id))
			.on(isStartTimeEqCurDate(curDate))
			.where(containsInNameOrAddress(word))
			.limit(pageable.getPageSize())
			.offset(pageable.getOffset())
			.transform(
				groupBy(venue.id).list(
					Projections.fields(VenueSearchData.class,
						venue.id,
						venue.thumbnailUrl,
						venue.name,
						venue.roadNameAddress.as(ADDRESS),
						venue.description,
						venue.location,
						list(
							Projections.fields(ShowInfo.class,
								show.startTime,
								show.endTime
							)).as(SHOWINFO)
					)
				)
			);

		JPAQuery<Long> venuesByWordCount = countSearchVenueList(word);

		return PageableExecutionUtils.getPage(venueSearchDataList, pageable, venuesByWordCount::fetchOne);
	}

	private JPAQuery<Long> countSearchVenueList(String word) {
		return query.select(venue.count())
			.from(venue)
			.where(containsInNameOrAddress(word));
	}

	public List<VenueSearchData> findVenueSearchById(Long venueId, LocalDate curDate) {
		return query.from(venue)
			.leftJoin(show)
			.on(venue.id.eq(show.venue.id))
			.on(isStartTimeEqCurDate(curDate))
			.where(venue.id.eq(venueId))
			.transform(
				groupBy(venue.id).list(
					Projections.fields(VenueSearchData.class,
						venue.id,
						venue.thumbnailUrl,
						venue.name,
						venue.roadNameAddress.as(ADDRESS),
						venue.description,
						venue.location,
						list(
							Projections.fields(ShowInfo.class,
								show.startTime,
								show.endTime
							)).as(SHOWINFO)
					)
				)
			);
	}

	public Page<VenueInfo> findVenuesByWord(String word, Pageable pageable) {
		List<VenueInfo> venueInfos = query.select(
				Projections.constructor(VenueInfo.class,
					venue.id,
					venue.name,
					venue.roadNameAddress.as(ADDRESS))
			)
			.from(venue)
			.where(containsInName(word))
			.limit(pageable.getPageSize())
			.offset(pageable.getOffset())
			.fetch();

		JPAQuery<Long> countVenuesByWordQuery = countVenuesByWord(word);

		return PageableExecutionUtils.getPage(venueInfos, pageable, countVenuesByWordQuery::fetchOne);
	}

	private JPAQuery<Long> countVenuesByWord(String word) {
		return query.select(venue.count())
			.from(venue)
			.where(containsInName(word));
	}

	private BooleanBuilder containsInNameOrAddress(String word) {
		BooleanBuilder booleanBuilder = new BooleanBuilder();
		booleanBuilder.or(containsInName(word));
		booleanBuilder.or(containsInAddress(word));

		return booleanBuilder;
	}

	private BooleanExpression containsInName(String word) {
		if (word == null || word.equals("")) {
			return null;
		}
		return venue.name.contains(word);
	}

	private BooleanExpression containsInAddress(String word) {
		if (word == null || word.equals("")) {
			return null;
		}
		return venue.roadNameAddress.contains(word);
	}

	private BooleanExpression isStartTimeEqCurDate(LocalDate curDate) {
		return Expressions.stringTemplate("DATE({0})", show.startTime)
			.eq(Expressions.stringTemplate("DATE({0})", curDate));
	}

	public Optional<VenueDetail> findVenue(Long venueId) {
		return query
			.from(venue)
			.leftJoin(venueImage).on(venue.id.eq(venueImage.venue.id))
			.leftJoin(image).on(venueImage.image.id.eq(image.id).and(image.status.eq(ImageStatus.REGISTERED)))
			.leftJoin(link).on(venue.id.eq(link.venue.id))
			.leftJoin(linkType).on(link.linkType.id.eq(linkType.id))
			.leftJoin(venueHour).on(venue.id.eq(venueHour.venue.id))
			.leftJoin(review).on(venue.id.eq(review.venue.id).and(review.status.eq(ReviewStatus.CREATED)))
			.leftJoin(admin).on(review.author.id.eq(admin.id))
			.where(venue.id.eq(venueId))
			.transform(
				groupBy(venue.id).list(
					Projections.fields(
						VenueDetail.class,
						venue.id,
						venue.name,
						venue.roadNameAddress,
						venue.lotNumberAddress,
						venue.phoneNumber,
						venue.description,
						venue.location,
						set(Projections.constructor(VenueDetailImage.class, image.id, image.url)).as("images"),
						set(Projections.constructor(VenueDetailLink.class, linkType.name, link.url)).as("links"),
						set(Projections.constructor(VenueDetailVenueHour.class, venueHour.day,
							venueHour.businessHour)).as("venueHours"),
						set(Projections.constructor(ReviewDetail.class, review.id, review.content, admin.loginId,
							review.likeCount, review.dislikeCount)).as("reviews")
					)
				)
			)
			.stream()
			.findFirst();
	}
}
