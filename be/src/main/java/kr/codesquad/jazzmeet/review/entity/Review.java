package kr.codesquad.jazzmeet.review.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
	@ManyToOne
	@JoinColumn(name = "author_id")
	private Admin author;
	@ManyToOne
	@JoinColumn(name = "venue_id")
	private Venue venue;
	@Enumerated(value = EnumType.STRING)
	private ReviewStatus status = ReviewStatus.CREATED;

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

	public boolean isAuthor(Admin user) {
		return author.isSame(user);
	}
}

