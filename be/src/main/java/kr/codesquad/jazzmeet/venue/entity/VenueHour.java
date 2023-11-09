package kr.codesquad.jazzmeet.venue.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class VenueHour {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Enumerated(value = EnumType.ORDINAL)
	@Column(nullable = false, length = 10)
	private DayOfWeek day;
	@Column(nullable = false, length = 20)
	private String businessHour;
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "venue_id")
	private Venue venue;

	@Builder
	public VenueHour(DayOfWeek day, String businessHour, Venue venue) {
		this.day = day;
		this.businessHour = businessHour;
		this.venue = venue;
	}

	// 연관 관계 편의 메서드
	public void add(Venue venue) {
		this.venue = venue;
		venue.getVenueHours().add(this);
	}

	public void addVenue(Venue venue) {
		this.venue = venue;
	}
}
