package kr.codesquad.jazzmeet.venue.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import kr.codesquad.jazzmeet.venue.dto.request.ReviewCreateRequest;
import kr.codesquad.jazzmeet.venue.dto.response.ReviewCreateResponse;
import kr.codesquad.jazzmeet.venue.service.ReviewFacade;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class ReviewController {

	private final ReviewFacade reviewFacade;

	/**
	 * 리뷰 등록 API
	 */
	@PostMapping("/api/reviews")
	public ResponseEntity<ReviewCreateResponse> createReview(@RequestBody ReviewCreateRequest request) {
		ReviewCreateResponse reviewCreateResponse = reviewFacade.createReview(request);

		return ResponseEntity.status(HttpStatus.CREATED).body(reviewCreateResponse);
	}
}
