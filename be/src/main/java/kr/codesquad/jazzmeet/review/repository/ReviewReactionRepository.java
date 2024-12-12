package kr.codesquad.jazzmeet.review.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.codesquad.jazzmeet.review.entity.ReactionType;
import kr.codesquad.jazzmeet.review.entity.ReviewReaction;

public interface ReviewReactionRepository extends JpaRepository<ReviewReaction, Long> {

	Optional<ReviewReaction> findByReviewIdAndUserIdAndReactionType(Long reviewId, Long userId, ReactionType reactionType);

	int countByReviewIdAndUserId(Long reviewId, Long userId);
}
