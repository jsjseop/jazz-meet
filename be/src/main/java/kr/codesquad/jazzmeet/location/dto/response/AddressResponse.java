package kr.codesquad.jazzmeet.location.dto.response;

import lombok.Builder;

@Builder
public record AddressResponse(
	String roadNameAddress,
	String lotNameAddress,
	Double latitude,
	Double longitude
) {
}
