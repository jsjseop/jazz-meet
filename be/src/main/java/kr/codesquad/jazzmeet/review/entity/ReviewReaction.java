package kr.codesquad.jazzmeet.review.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import kr.codesquad.jazzmeet.admin.entity.Admin;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewReaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private Admin user;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "review_id")
	private Review review;
	@Enumerated(EnumType.STRING)
	private ReactionType reactionType;

	public ReviewReaction(Admin user, Review review, ReactionType reactionType) {
		this.user = user;
		this.review = review;
		this.reactionType = reactionType;
	}

	public void setReview(Review review) {
		this.review = review;
	}
}
