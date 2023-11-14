package kr.codesquad.jazzmeet.venue.dto.request;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import kr.codesquad.jazzmeet.venue.dto.annotation.Latitude;
import kr.codesquad.jazzmeet.venue.dto.annotation.Longitude;
import lombok.Builder;

@Builder
public record VenueCreateRequest(
	String name,
	@Size(max = 10)
	List<Long> imageIds,
	String roadNameAddress,
	String lotNumberAddress,
	String phoneNumber,
	String description,
	List<VenueLinkRequest> links,
	List<VenueHourRequest> venueHours,
	@NotNull
	@Latitude
	Double latitude,
	@NotNull
	@Longitude
	Double longitude
) {
}
