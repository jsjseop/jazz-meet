package kr.codesquad.jazzmeet.location.dto.response;

import java.util.List;

import lombok.Builder;

@Builder
public record AddressSearchResponse(
	List<AddressResponse> addresses,
	long totalCount,
	int currentPage,
	int maxPage
) {
}
