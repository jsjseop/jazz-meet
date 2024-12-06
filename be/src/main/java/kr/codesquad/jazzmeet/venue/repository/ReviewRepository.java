package kr.codesquad.jazzmeet.venue.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.codesquad.jazzmeet.venue.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
