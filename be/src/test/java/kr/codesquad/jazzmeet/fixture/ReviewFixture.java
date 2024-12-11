package kr.codesquad.jazzmeet.fixture;

import kr.codesquad.jazzmeet.admin.entity.Admin;
import kr.codesquad.jazzmeet.review.entity.Review;

public class ReviewFixture {

	public static Review createReview(Admin user, String password, String content) {
		return Review.builder()
			.author(user)
			.content(content)
			.build();
	}
}
