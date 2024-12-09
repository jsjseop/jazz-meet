package kr.codesquad.jazzmeet.venue.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import kr.codesquad.jazzmeet.fixture.VenueFixture;
import kr.codesquad.jazzmeet.venue.dto.request.ReviewCreateRequest;
import kr.codesquad.jazzmeet.venue.dto.response.ReviewCreateResponse;
import kr.codesquad.jazzmeet.venue.entity.Review;
import kr.codesquad.jazzmeet.venue.entity.Venue;

@ExtendWith(MockitoExtension.class)
public class ReviewFacadeTest {

	@InjectMocks
	ReviewFacade reviewFacade;
	@Mock
	BCryptPasswordEncoder bCryptPasswordEncoder;
	@Mock
	VenueService venueService;
	@Mock
	ReviewService reviewService;

	@Test
	@DisplayName("리뷰를 등록한다.")
	void createReview() {
		// given
		ReviewCreateRequest reviewCreateRequest = new ReviewCreateRequest("user", "1234", "리뷰 내용", 1L);

		when(bCryptPasswordEncoder.encode(anyString())).thenReturn("encodedPassword");
		Venue venue = VenueFixture.createVenue("공연장", "주소");
		when(venueService.findById(anyLong())).thenReturn(venue);
		Review review = Review.builder()
			.nickname("user").password("1234")
			.content("리뷰 내용").build();
		when(reviewService.save(any(Review.class))).thenReturn(review);

		// when(reviewMapper.toReview(any(ReviewCreateRequest.class), anyString(), any(Venue.class))).thenReturn(review);
		// when(reviewMapper.toReviewCreateResponse(any(Review.class))).thenReturn(reviewCreateResponse);

		// when
		ReviewCreateResponse result = reviewFacade.createReview(reviewCreateRequest);

		// then
		assertThat(result.nickname()).isEqualTo(reviewCreateRequest.nickname());
		assertThat(result.content()).isEqualTo(reviewCreateRequest.content());

		verify(bCryptPasswordEncoder, times(1)).encode(reviewCreateRequest.password());
		verify(venueService, times(1)).findById(reviewCreateRequest.venueId());
		verify(reviewService, times(1)).save(any(Review.class));
	}
}
