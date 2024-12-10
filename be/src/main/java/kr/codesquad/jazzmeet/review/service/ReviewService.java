package kr.codesquad.jazzmeet.review.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.codesquad.jazzmeet.global.error.CustomException;
import kr.codesquad.jazzmeet.global.error.statuscode.VenueErrorCode;
import kr.codesquad.jazzmeet.review.dto.request.ReviewUpdateRequest;
import kr.codesquad.jazzmeet.review.dto.response.ReviewUpdateResponse;
import kr.codesquad.jazzmeet.review.entity.Review;
import kr.codesquad.jazzmeet.review.mapper.ReviewMapper;
import kr.codesquad.jazzmeet.review.repository.ReviewRepository;
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

