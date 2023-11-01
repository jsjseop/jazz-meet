package kr.codesquad.jazzmeet.venue.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import kr.codesquad.jazzmeet.venue.dto.ShowInfo;
import kr.codesquad.jazzmeet.venue.dto.VenueSearch;
import kr.codesquad.jazzmeet.venue.dto.response.NearbyVenueResponse;
import kr.codesquad.jazzmeet.venue.dto.response.VenueAutocompleteResponse;
import kr.codesquad.jazzmeet.venue.dto.response.VenuePinsBySearchResponse;
import kr.codesquad.jazzmeet.venue.dto.response.VenueSearchResponse;
import kr.codesquad.jazzmeet.venue.entity.Venue;
import kr.codesquad.jazzmeet.venue.vo.NearbyVenue;
import kr.codesquad.jazzmeet.venue.vo.ShowInfoData;
import kr.codesquad.jazzmeet.venue.vo.VenuePinsByWord;
import kr.codesquad.jazzmeet.venue.vo.VenueSearchData;

@Mapper
public interface VenueMapper {
	VenueMapper INSTANCE = Mappers.getMapper(VenueMapper.class);

	@Mapping(target = "address", source = "roadNameAddress")
	@Mapping(target = "latitude", source = "location.y")
	@Mapping(target = "longitude", source = "location.x")
	VenueAutocompleteResponse toVenueAutocompleteResponse(Venue venue);

	@Mapping(target = "latitude", source = "location.y")
	@Mapping(target = "longitude", source = "location.x")
	NearbyVenueResponse toNearByVenueResponse(NearbyVenue nearByVenue);

	@Mapping(target = "latitude", source = "location.y")
	@Mapping(target = "longitude", source = "location.x")
	VenuePinsBySearchResponse toVenuePinsBySearchResponse(VenuePinsByWord venuePinsByWord);

	default VenueSearchResponse toVenueSearchResponse(List<VenueSearch> venueSearchList, int venueCount,
		int currentPage,
		int maxPage) {
		Integer dummy = null;
		return toVenueSearchResponse(dummy, venueSearchList, venueCount, currentPage, maxPage);
	}

	@Mapping(target = "venues", source = "venueSearchList")
	VenueSearchResponse toVenueSearchResponse(Integer dummy, List<VenueSearch> venueSearchList, int venueCount,
		int currentPage,
		int maxPage);

	default VenueSearch toVenueSearch(VenueSearchData venueSearchData, List<ShowInfo> showInfo) {
		Integer dummy = null;
		return toVenueSearch(dummy, venueSearchData, showInfo);
	}

	@Mapping(target = "latitude", source = "venueSearchData.location.y")
	@Mapping(target = "longitude", source = "venueSearchData.location.x")
	@Mapping(target = "showInfo", source = "showInfo")
	VenueSearch toVenueSearch(Integer dummy, VenueSearchData venueSearchData, List<ShowInfo> showInfo);

	ShowInfo toShowInfo(ShowInfoData showInfoData);

}
