package kr.codesquad.jazzmeet.review.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import kr.codesquad.jazzmeet.review.dto.request.ReviewCreateRequest;
import kr.codesquad.jazzmeet.review.dto.request.ReviewDeleteRequest;
import kr.codesquad.jazzmeet.review.dto.request.ReviewUpdateRequest;
import kr.codesquad.jazzmeet.review.dto.response.ReviewCreateResponse;
import kr.codesquad.jazzmeet.review.dto.response.ReviewUpdateResponse;
import kr.codesquad.jazzmeet.review.service.ReviewFacade;
import kr.codesquad.jazzmeet.review.service.ReviewService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class ReviewController {

	private final ReviewFacade reviewFacade;
	private final ReviewService reviewService;

	/**
	 * 리뷰 등록 API
	 */
	@PostMapping("/api/reviews")
	public ResponseEntity<ReviewCreateResponse> createReview(@RequestBody ReviewCreateRequest reviewCreateRequest) {
		ReviewCreateResponse reviewCreateResponse = reviewFacade.createReview(reviewCreateRequest);

		return ResponseEntity.status(HttpStatus.CREATED).body(reviewCreateResponse);
	}

	/**
	 * 리뷰 수정 API
	 */
	@PutMapping("/api/reviews/{reviewId}")
	public ResponseEntity<ReviewUpdateResponse> updateReview(@PathVariable Long reviewId,
		@RequestBody ReviewUpdateRequest reviewUpdateRequest) {
		ReviewUpdateResponse reviewUpdateResponse = reviewService.updateReview(reviewId, reviewUpdateRequest);

		return ResponseEntity.status(HttpStatus.OK).body(reviewUpdateResponse);
	}

	/**
	 * 리뷰 삭제 API
	 */
	@DeleteMapping("/api/reviews/{reviewId}")
	public ResponseEntity<Void> deleteReview(@PathVariable Long reviewId,
		@RequestBody ReviewDeleteRequest reviewDeleteRequest) {
		reviewService.deleteReview(reviewId, reviewDeleteRequest);

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
