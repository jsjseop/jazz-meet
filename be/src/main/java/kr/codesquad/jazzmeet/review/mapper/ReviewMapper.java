package kr.codesquad.jazzmeet.review.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import kr.codesquad.jazzmeet.admin.entity.Admin;
import kr.codesquad.jazzmeet.review.dto.request.ReviewCreateRequest;
import kr.codesquad.jazzmeet.review.dto.response.ReviewCreateResponse;
import kr.codesquad.jazzmeet.review.dto.response.ReviewUpdateResponse;
import kr.codesquad.jazzmeet.review.entity.Review;
import kr.codesquad.jazzmeet.venue.entity.Venue;

@Mapper
public interface ReviewMapper {
	ReviewMapper INSTANCE = Mappers.getMapper(ReviewMapper.class);

	Review toReview(ReviewCreateRequest reviewCreateRequest, Admin author, Venue venue);

	@Mapping(target = "userId", source = "author.loginId")
	ReviewCreateResponse toReviewCreateResponse(Review review);

	ReviewUpdateResponse toReviewUpdateResponse(Review review);
}
