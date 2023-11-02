package kr.codesquad.jazzmeet.venue.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import kr.codesquad.jazzmeet.image.entity.Image;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class VenueImage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private Long imageOrder;
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "venue_id")
	private Venue venue;
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "image_id")
	private Image image;

	@Builder
	public VenueImage(Long imageOrder, Venue venue, Image image) {
		this.imageOrder = imageOrder;
		this.venue = venue;
		this.image = image;
	}

	// 연관관계 편의 메서드
	public void add(Venue venue, Image image) {
		this.venue = venue;
		this.image = image;
		venue.getImages().add(this);
	}
}
