package kr.codesquad.jazzmeet.venue.mapper;

import java.util.ArrayList;
import java.util.List;

import org.locationtech.jts.geom.Point;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import kr.codesquad.jazzmeet.venue.dto.ShowInfo;
import kr.codesquad.jazzmeet.venue.dto.VenueInfo;
import kr.codesquad.jazzmeet.venue.dto.VenueSearch;
import kr.codesquad.jazzmeet.venue.dto.request.VenueCreateRequest;
import kr.codesquad.jazzmeet.venue.dto.response.NearbyVenueResponse;
import kr.codesquad.jazzmeet.venue.dto.response.VenueAutocompleteResponse;
import kr.codesquad.jazzmeet.venue.dto.response.VenueDetailResponse;
import kr.codesquad.jazzmeet.venue.dto.response.VenueListResponse;
import kr.codesquad.jazzmeet.venue.dto.response.VenuePinsResponse;
import kr.codesquad.jazzmeet.venue.dto.response.VenueSearchResponse;
import kr.codesquad.jazzmeet.venue.entity.Venue;
import kr.codesquad.jazzmeet.venue.vo.NearbyVenue;
import kr.codesquad.jazzmeet.venue.vo.VenueDetail;
import kr.codesquad.jazzmeet.venue.vo.VenueDetailImage;
import kr.codesquad.jazzmeet.venue.vo.VenueDetailLink;
import kr.codesquad.jazzmeet.venue.vo.VenueDetailVenueHour;
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

	default VenueSearchResponse toVenueSearchResponse(Page<VenueSearchData> venueSearchList, int currentPage) {
		List<VenueSearchData> content = venueSearchList.getContent();
		if (!venueSearchList.hasContent()) {
			content = new ArrayList<>();
		}
		return toVenueSearchResponse(content, venueSearchList, currentPage);
	}

	@Mapping(target = "totalCount", source = "venueSearchList.totalElements")
	@Mapping(target = "maxPage", source = "venueSearchList.totalPages")
	VenueSearchResponse toVenueSearchResponse(List<VenueSearchData> venues, Page<VenueSearchData> venueSearchList,
		int currentPage);

	@Mapping(target = "venues", source = "venueSearchList")
	VenueSearchResponse toVenueSearchResponse(List<VenueSearchData> venueSearchList, int totalCount, int currentPage,
		int maxPage);

	default VenueSearch toVenueSearch(VenueSearchData venueSearchData) {
		List<ShowInfo> showInfo = venueSearchData.getShowInfo();
		if ((showInfo.size() == 1) && showInfo.get(0).emptyCheck()) {
			showInfo = new ArrayList<>();
		}
		return toVenueSearch(venueSearchData, showInfo);
	}

	@Mapping(target = "latitude", source = "venueSearchData.location.y")
	@Mapping(target = "longitude", source = "venueSearchData.location.x")
	@Mapping(target = "showInfo", source = "showInfoList")
	VenueSearch toVenueSearch(VenueSearchData venueSearchData, List<ShowInfo> showInfoList);

	@Mapping(target = "venues", source = "venueInfos.content")
	@Mapping(target = "totalCount", source = "venueInfos.totalElements")
	@Mapping(target = "maxPage", source = "venueInfos.totalPages")
	VenueListResponse toVenueListResponse(Page<VenueInfo> venueInfos, int currentPage);

	@Mapping(target = "latitude", source = "location.y")
	@Mapping(target = "longitude", source = "location.x")
	VenueDetailResponse toVenueDetailResponse(VenueDetail venueDetail);

	@Mapping(target = "images", source = "images")
	@Mapping(target = "links", source = "links")
	@Mapping(target = "venueHours", source = "venueHours")
	VenueDetail toVenueDetail(Venue venue,
		List<VenueDetailImage> images, List<VenueDetailLink> links, List<VenueDetailVenueHour> venueHours);

	Venue toVenue(VenueCreateRequest venueCreateRequest, Point location, String thumbnailUrl);
}
