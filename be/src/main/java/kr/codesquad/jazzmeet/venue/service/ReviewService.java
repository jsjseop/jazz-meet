package kr.codesquad.jazzmeet.venue.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.codesquad.jazzmeet.venue.entity.Review;
import kr.codesquad.jazzmeet.venue.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ReviewService {

	private final ReviewRepository reviewRepository;

	@Transactional
	public Review save(Review review) {
		return reviewRepository.save(review);
	}
}

