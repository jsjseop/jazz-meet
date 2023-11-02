package kr.codesquad.jazzmeet.venue.dto.response;

import java.util.List;

import kr.codesquad.jazzmeet.venue.dto.VenueSearch;
import lombok.Builder;

@Builder
public record VenueSearchResponse(
	List<VenueSearch> venues,
	Long venueCount,
	int currentPage,
	int maxPage) {
	public static VenueSearchResponse emptyVenues() {
		return VenueSearchResponse.builder().venues(List.of()).venueCount(0L).currentPage(1).maxPage(1).build();
	}
}
