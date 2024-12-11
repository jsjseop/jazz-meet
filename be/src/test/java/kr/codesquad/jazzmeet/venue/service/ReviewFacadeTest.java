package kr.codesquad.jazzmeet.venue.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import kr.codesquad.jazzmeet.admin.entity.Admin;
import kr.codesquad.jazzmeet.admin.entity.UserRole;
import kr.codesquad.jazzmeet.fixture.AdminFixture;
import kr.codesquad.jazzmeet.fixture.ReviewFixture;
import kr.codesquad.jazzmeet.fixture.VenueFixture;
import kr.codesquad.jazzmeet.review.dto.request.ReviewCreateRequest;
import kr.codesquad.jazzmeet.review.dto.response.ReviewCreateResponse;
import kr.codesquad.jazzmeet.review.entity.Review;
import kr.codesquad.jazzmeet.review.service.ReviewFacade;
import kr.codesquad.jazzmeet.review.service.ReviewService;
import kr.codesquad.jazzmeet.venue.entity.Venue;

@ExtendWith(MockitoExtension.class)
class ReviewFacadeTest {

	@InjectMocks
	ReviewFacade reviewFacade;
	@Mock
	VenueService venueService;
	@Mock
	ReviewService reviewService;

	@Test
	@DisplayName("리뷰를 등록한다.")
	void createReview() {
		// given
		ReviewCreateRequest reviewCreateRequest = new ReviewCreateRequest("리뷰 내용", 1L);
		Admin user = AdminFixture.createAdmin("user", "1234", UserRole.USER);

		Venue venue = VenueFixture.createVenue("공연장", "주소");
		when(venueService.findById(anyLong())).thenReturn(venue);
		Review review = ReviewFixture.createReview(user, "1234", "리뷰 내용");
		when(reviewService.save(any(Review.class))).thenReturn(review);

		// when(reviewMapper.toReview(any(ReviewCreateRequest.class), anyString(), any(Venue.class))).thenReturn(review);
		// when(reviewMapper.toReviewCreateResponse(any(Review.class))).thenReturn(reviewCreateResponse);

		// when
		ReviewCreateResponse result = reviewFacade.createReview(user, reviewCreateRequest);

		// then
		assertThat(result.userId()).isEqualTo(user.getLoginId());
		assertThat(result.content()).isEqualTo(reviewCreateRequest.content());

		verify(venueService, times(1)).findById(reviewCreateRequest.venueId());
		verify(reviewService, times(1)).save(any(Review.class));
	}
}
