package kr.codesquad.jazzmeet.venue.mapper;

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import kr.codesquad.jazzmeet.venue.dto.ShowInfo;
import kr.codesquad.jazzmeet.venue.dto.VenueSearch;
import kr.codesquad.jazzmeet.venue.dto.response.NearbyVenueResponse;
import kr.codesquad.jazzmeet.venue.dto.response.VenueAutocompleteResponse;
import kr.codesquad.jazzmeet.venue.dto.response.VenueDetailResponse;
import kr.codesquad.jazzmeet.venue.dto.response.VenuePinsResponse;
import kr.codesquad.jazzmeet.venue.dto.response.VenueSearchResponse;
import kr.codesquad.jazzmeet.venue.entity.Venue;
import kr.codesquad.jazzmeet.venue.vo.NearbyVenue;
import kr.codesquad.jazzmeet.venue.vo.VenueDetail;
import kr.codesquad.jazzmeet.venue.vo.VenuePins;
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
	VenuePinsResponse toVenuePinsBySearchResponse(VenuePins venuePinsByWord);

	default VenueSearchResponse toVenueSearchResponse(List<VenueSearch> venueSearchList,
		long venueCount, int currentPage, long maxPage) {
		Integer dummy = null;
		return toVenueSearchResponse(dummy, venueSearchList, venueCount, currentPage, maxPage);
	}

	@Mapping(target = "venues", source = "venueSearchList")
	VenueSearchResponse toVenueSearchResponse(Integer dummy, List<VenueSearch> venueSearchList,
		long venueCount, int currentPage, long maxPage);

	default VenueSearch toVenueSearch(VenueSearchData venueSearchData) {
		Integer dummy = null;
		List<ShowInfo> showInfo = venueSearchData.getShowInfo();
		if ((showInfo.size() == 1) && showInfo.get(0).emptyCheck()) {
			showInfo = new ArrayList<>();
		}
		return toVenueSearch(dummy, venueSearchData, showInfo);
	}

	@Mapping(target = "latitude", source = "venueSearchData.location.y")
	@Mapping(target = "longitude", source = "venueSearchData.location.x")
	@Mapping(target = "showInfo", source = "showInfoList")
	VenueSearch toVenueSearch(Integer dummy, VenueSearchData venueSearchData, List<ShowInfo> showInfoList);

	@Mapping(target = "latitude", source = "location.y")
	@Mapping(target = "longitude", source = "location.x")
	VenueDetailResponse toVenueDetailResponse(VenueDetail venueDetail);
}
