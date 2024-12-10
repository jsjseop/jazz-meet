package kr.codesquad.jazzmeet.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.codesquad.jazzmeet.review.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
