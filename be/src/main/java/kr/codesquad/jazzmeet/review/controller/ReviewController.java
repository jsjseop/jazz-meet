package kr.codesquad.jazzmeet.review.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import kr.codesquad.jazzmeet.admin.entity.Admin;
import kr.codesquad.jazzmeet.global.permission.AdminAuth;
import kr.codesquad.jazzmeet.global.permission.LoginPermission;
import kr.codesquad.jazzmeet.review.dto.request.ReviewCreateRequest;
import kr.codesquad.jazzmeet.review.dto.request.ReviewUpdateRequest;
import kr.codesquad.jazzmeet.review.dto.response.ReviewCreateResponse;
import kr.codesquad.jazzmeet.review.dto.response.ReviewUpdateResponse;
import kr.codesquad.jazzmeet.review.entity.ReactionType;
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
	@LoginPermission
	@PostMapping("/api/reviews")
	public ResponseEntity<ReviewCreateResponse> createReview(@AdminAuth Admin user,
		@RequestBody ReviewCreateRequest reviewCreateRequest) {
		ReviewCreateResponse reviewCreateResponse = reviewFacade.createReview(user, reviewCreateRequest);

		return ResponseEntity.status(HttpStatus.CREATED).body(reviewCreateResponse);
	}

	/**
	 * 리뷰 수정 API
	 */
	@LoginPermission
	@PutMapping("/api/reviews/{reviewId}")
	public ResponseEntity<ReviewUpdateResponse> updateReview(@PathVariable Long reviewId, @AdminAuth Admin user,
		@RequestBody ReviewUpdateRequest reviewUpdateRequest) {
		ReviewUpdateResponse reviewUpdateResponse = reviewService.updateReview(reviewId, user, reviewUpdateRequest);

		return ResponseEntity.status(HttpStatus.OK).body(reviewUpdateResponse);
	}

	/**
	 * 리뷰 삭제 API
	 */
	@LoginPermission
	@DeleteMapping("/api/reviews/{reviewId}")
	public ResponseEntity<Void> deleteReview(@PathVariable Long reviewId, @AdminAuth Admin user) {
		reviewService.deleteReview(reviewId, user);

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	/**
	 * 리뷰 좋아요 반응 추가 API
	 */
	@LoginPermission
	@PostMapping("/api/reviews/{reviewId}/reactions/like")
	public ResponseEntity<Void> createLike(@PathVariable Long reviewId, @AdminAuth Admin user) {
		reviewService.createReaction(reviewId, user, ReactionType.LIKE);

		return ResponseEntity.noContent().build();
	}

	/**
	 * 리뷰 좋아요 반응 삭제 API
	 */
	@LoginPermission
	@DeleteMapping("/api/reviews/{reviewId}/reactions/like")
	public ResponseEntity<Void> deleteLike(@PathVariable Long reviewId, @AdminAuth Admin user) {
		reviewService.deleteReaction(reviewId, user, ReactionType.LIKE);

		return ResponseEntity.noContent().build();
	}

	/**
	 * 리뷰 싫어요 반응 추가 API
	 */
	@LoginPermission
	@PostMapping("/api/reviews/{reviewId}/reactions/dislike")
	public ResponseEntity<Void> createDislike(@PathVariable Long reviewId, @AdminAuth Admin user) {
		reviewService.createReaction(reviewId, user, ReactionType.DISLIKE);

		return ResponseEntity.noContent().build();
	}

	/**
	 * 리뷰 싫어요 반응 삭제 API
	 */
	@LoginPermission
	@DeleteMapping("/api/reviews/{reviewId}/reactions/dislike")
	public ResponseEntity<Void> deleteDislike(@PathVariable Long reviewId, @AdminAuth Admin user) {
		reviewService.deleteReaction(reviewId, user, ReactionType.DISLIKE);

		return ResponseEntity.noContent().build();
	}
}
