package kr.codesquad.jazzmeet.venue.dto.response;

import lombok.Builder;

@Builder
public record VenuePinsResponse(
	Long id,
	String name,
	Double latitude,
	Double longitude
) {
}
