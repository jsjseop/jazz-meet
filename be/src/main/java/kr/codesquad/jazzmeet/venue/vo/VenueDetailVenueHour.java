package kr.codesquad.jazzmeet.venue.vo;

import kr.codesquad.jazzmeet.venue.entity.DayOfWeek;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class VenueDetailVenueHour {
	private String day;
	private String businessHours;

	public VenueDetailVenueHour(DayOfWeek day, String businessHours) {
		this.day = DayOfWeek.getName(day.ordinal());
		this.businessHours = businessHours;
	}
}
