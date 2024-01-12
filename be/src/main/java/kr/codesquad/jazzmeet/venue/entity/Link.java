package kr.codesquad.jazzmeet.venue.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Link {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false, length = 500)
	private String url;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "link_type_id")
	private LinkType linkType;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "venue_id")
	private Venue venue;

	@Builder
	public Link(Long id, String url, LinkType linkType, Venue venue) {
		this.id = id;
		this.url = url;
		this.linkType = linkType;
		this.venue = venue;
	}

	public void addVenue(Venue venue) {
		this.venue = venue;
	}
}
