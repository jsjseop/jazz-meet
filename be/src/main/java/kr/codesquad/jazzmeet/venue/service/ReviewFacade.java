package kr.codesquad.jazzmeet.venue.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.codesquad.jazzmeet.global.util.PasswordEncoder;
import kr.codesquad.jazzmeet.venue.dto.request.ReviewCreateRequest;
import kr.codesquad.jazzmeet.venue.dto.response.ReviewCreateResponse;
import kr.codesquad.jazzmeet.venue.entity.Review;
import kr.codesquad.jazzmeet.venue.entity.Venue;
import kr.codesquad.jazzmeet.venue.mapper.ReviewMapper;
import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class ReviewFacade {

	private final ReviewService reviewService;
	private final VenueService venueService;

	@Transactional
	public ReviewCreateResponse createReview(ReviewCreateRequest reviewCreateRequest) {
		String encryptedPassword = PasswordEncoder.encode(reviewCreateRequest.password());
		Venue venue = venueService.findById(reviewCreateRequest.venueId());
		Review review = ReviewMapper.INSTANCE.toReview(reviewCreateRequest, encryptedPassword, venue);
		Review savedReview = reviewService.save(review);

		return ReviewMapper.INSTANCE.toReviewCreateResponse(savedReview);
	}
}
