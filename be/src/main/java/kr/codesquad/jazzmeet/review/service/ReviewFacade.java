package kr.codesquad.jazzmeet.review.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.codesquad.jazzmeet.review.dto.request.ReviewCreateRequest;
import kr.codesquad.jazzmeet.review.dto.response.ReviewCreateResponse;
import kr.codesquad.jazzmeet.review.entity.Review;
import kr.codesquad.jazzmeet.venue.entity.Venue;
import kr.codesquad.jazzmeet.review.mapper.ReviewMapper;
import kr.codesquad.jazzmeet.venue.service.VenueService;
import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class ReviewFacade {

	private final ReviewService reviewService;
	private final VenueService venueService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	@Transactional
	public ReviewCreateResponse createReview(ReviewCreateRequest reviewCreateRequest) {
		String encryptedPassword = bCryptPasswordEncoder.encode(reviewCreateRequest.password());
		Venue venue = venueService.findById(reviewCreateRequest.venueId());
		Review review = ReviewMapper.INSTANCE.toReview(reviewCreateRequest, encryptedPassword, venue);
		Review savedReview = reviewService.save(review);

		return ReviewMapper.INSTANCE.toReviewCreateResponse(savedReview);
	}
}
