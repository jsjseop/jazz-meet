package kr.codesquad.jazzmeet.venue.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import kr.codesquad.jazzmeet.venue.dto.request.ReviewCreateRequest;
import kr.codesquad.jazzmeet.venue.dto.response.ReviewCreateResponse;
import kr.codesquad.jazzmeet.venue.entity.Review;
import kr.codesquad.jazzmeet.venue.entity.Venue;

@Mapper
public interface ReviewMapper {
	ReviewMapper INSTANCE = Mappers.getMapper(ReviewMapper.class);

	@Mapping(target = "password", source = "encryptedPassword")
	Review toReview(ReviewCreateRequest reviewCreateRequest, String encryptedPassword, Venue venue);

	ReviewCreateResponse toReviewCreateResponse(Review review);
}
