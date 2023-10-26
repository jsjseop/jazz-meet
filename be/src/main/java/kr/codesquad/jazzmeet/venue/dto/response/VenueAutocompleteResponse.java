package kr.codesquad.jazzmeet.venue.dto.response;

import lombok.Builder;

@Builder
public record VenueAutocompleteResponse(
	Long id,
	String name,
	String address,
	Double latitude,
	Double longitude) {
}
