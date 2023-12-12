package kr.codesquad.jazzmeet.venue.dto.request;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import kr.codesquad.jazzmeet.venue.controller.annotation.Latitude;
import kr.codesquad.jazzmeet.venue.controller.annotation.Longitude;
import lombok.Builder;

@Builder
public record VenueUpdateRequest(
	@NotNull
	@Size(max = 50)
	String name,
	@Size(max = 10)
	List<Long> imageIds,
	@NotNull
	@Size(max = 50)
	String roadNameAddress,
	@NotNull
	@Size(max = 50)
	String lotNumberAddress,
	@Size(max = 20)
	String phoneNumber,
	@Size(max = 1000)
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
