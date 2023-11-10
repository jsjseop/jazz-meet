package kr.codesquad.jazzmeet.venue.dto.request;

import java.util.List;

import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record VenueUpdateRequest(
	String name,
	@Size(max = 10)
	List<Long> imageIds,
	String roadNameAddress,
	String lotNumberAddress,
	String phoneNumber,
	String description,
	List<VenueLinkRequest> links,
	List<VenueHourRequest> venueHours,
	double latitude,
	double longitude
) {
}
