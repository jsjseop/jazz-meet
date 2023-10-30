package kr.codesquad.jazzmeet.venue.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class VenueHours {
	@OneToMany(mappedBy = "venue", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<VenueHour> venueHours;
}
