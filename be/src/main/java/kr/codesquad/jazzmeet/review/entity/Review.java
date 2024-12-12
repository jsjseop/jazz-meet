package kr.codesquad.jazzmeet.review.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import kr.codesquad.jazzmeet.admin.entity.Admin;
import kr.codesquad.jazzmeet.global.time.BaseTimeEntity;
import kr.codesquad.jazzmeet.venue.entity.Venue;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Review extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String content;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "author_id")
	private Admin author;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "venue_id")
	private Venue venue;
	@Enumerated(value = EnumType.STRING)
	private ReviewStatus status = ReviewStatus.CREATED;
	@OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ReviewReaction> reactions = new ArrayList<>();

	private int likeCount;
	private int dislikeCount;

	@Builder
	public Review(Admin author, String content, Venue venue) {
		this.author = author;
		this.content = content;
		this.venue = venue;
	}

	public void updateContent(String content) {
		this.content = content;
	}

	public void deleteReview() {
		this.status = ReviewStatus.DELETED;
	}

	public boolean isNotAuthor(Admin user) {
		return author.isSame(user);
	}

	public void updateReactionCount(ReactionType reactionType, boolean isIncrement) {
		int changeAmount = isIncrement ? 1 : -1;
		if (reactionType.equals(ReactionType.LIKE)) {
			likeCount += changeAmount;
		} else {
			dislikeCount += changeAmount;
		}
	}

	public void addReviewReaction(ReviewReaction reviewReaction) {
		reactions.add(reviewReaction);
		reviewReaction.setReview(this);
	}

	public void deleteReviewReaction(ReviewReaction reviewReaction) {
		reactions.remove(reviewReaction);
		reviewReaction.setReview(null);
	}
}

