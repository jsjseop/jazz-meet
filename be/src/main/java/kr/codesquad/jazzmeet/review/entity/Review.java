package kr.codesquad.jazzmeet.review.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
	private String nickname;
	private String password;
	private String content;
	@ManyToOne
	@JoinColumn(name = "venue_id")
	private Venue venue;
	@Enumerated(value = EnumType.STRING)
	private ReviewStatus status = ReviewStatus.CREATED;

	@Builder
	public Review(String nickname, String password, String content, Venue venue) {
		this.nickname = nickname;
		this.password = password;
		this.content = content;
		this.venue = venue;
	}

	public void updateContent(String content) {
		this.content = content;
	}

	public void deleteReview() {
		this.status = ReviewStatus.DELETED;
	}
}

