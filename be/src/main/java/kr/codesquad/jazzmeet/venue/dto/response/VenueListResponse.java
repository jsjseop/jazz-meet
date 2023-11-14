package kr.codesquad.jazzmeet.venue.dto.response;

import java.util.List;

import kr.codesquad.jazzmeet.venue.dto.VenueInfo;

public record VenueListResponse(
	List<VenueInfo> venues,
	long totalCount,
	int currentPage,
	int maxPage
) {
}
