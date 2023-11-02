package kr.codesquad.jazzmeet.venue.dto.response;

import java.util.List;

import kr.codesquad.jazzmeet.venue.dto.VenueSearch;
import lombok.Builder;

@Builder
public record VenueSearchResponse(
	List<VenueSearch> venues,
	long venueCount,
	int currentPage,
	long maxPage) {
	public static VenueSearchResponse emptyVenues() {
		return VenueSearchResponse.builder().venues(List.of()).build();
	}
}
