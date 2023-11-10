package kr.codesquad.jazzmeet.venue.dto.request;

import java.util.List;

import lombok.Builder;

@Builder
public record VenueCreateRequest(
	String name,
	List<Long> imageIds,
	String roadNameAddress,
	String lotNumberAddress,
	String phoneNumber,
	String description,
	List<VenueCreateLink> links,
	List<VenueCreateHour> venueHours,
	double latitude,
	double longitude
) {
}
