package kr.codesquad.jazzmeet.address.dto.response;

import lombok.Builder;

@Builder
public record AddressResponse(
	String roadNameAddress,
	String lotNumberAddress,
	Double latitude,
	Double longitude
) {
}
