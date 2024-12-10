package kr.codesquad.jazzmeet.venue.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.codesquad.jazzmeet.global.error.CustomException;
import kr.codesquad.jazzmeet.global.error.statuscode.VenueErrorCode;
import kr.codesquad.jazzmeet.venue.dto.request.ReviewUpdateRequest;
import kr.codesquad.jazzmeet.venue.dto.response.ReviewUpdateResponse;
import kr.codesquad.jazzmeet.venue.entity.Review;
import kr.codesquad.jazzmeet.venue.mapper.ReviewMapper;
import kr.codesquad.jazzmeet.venue.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ReviewService {

	private final ReviewRepository reviewRepository;

	@Transactional
	public Review save(Review review) {
		return reviewRepository.save(review);
	}

	public Review findById(Long reviewId) {
		return reviewRepository.findById(reviewId)
			.orElseThrow(() -> new CustomException(VenueErrorCode.NOT_FOUND_REVIEW));
	}

	@Transactional
	public ReviewUpdateResponse updateReview(Long reviewId, ReviewUpdateRequest reviewUpdateRequest) {
		Review review = findById(reviewId);
		review.updateContent(reviewUpdateRequest.content());

		return ReviewMapper.INSTANCE.toReviewUpdateResponse(review);
	}
}

