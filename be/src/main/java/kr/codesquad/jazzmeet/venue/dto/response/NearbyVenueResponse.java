package kr.codesquad.jazzmeet.venue.dto.response;

public record NearbyVenueResponse(
	Long id,
	String name,
	String address,
	String thumbnailUrl,
	Double latitude,
	Double longitude) {
}
