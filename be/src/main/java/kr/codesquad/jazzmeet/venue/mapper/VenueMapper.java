package kr.codesquad.jazzmeet.venue.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import kr.codesquad.jazzmeet.venue.dto.response.NearbyVenueResponse;
import kr.codesquad.jazzmeet.venue.dto.response.VenueAutocompleteResponse;
import kr.codesquad.jazzmeet.venue.entity.Venue;
import kr.codesquad.jazzmeet.venue.vo.NearbyVenue;

@Mapper
public interface VenueMapper {
	VenueMapper INSTANCE = Mappers.getMapper(VenueMapper.class);

	@Mapping(target = "address", source = "roadNameAddress")
	@Mapping(target = "latitude", source = "location.x")
	@Mapping(target = "longitude", source = "location.y")
	VenueAutocompleteResponse toVenueAutocompleteResponse(Venue venue);

	@Mapping(target = "latitude", source = "location.y")
	@Mapping(target = "longitude", source = "location.x")
	NearbyVenueResponse toNearByVenueResponse(NearbyVenue nearByVenue);
}
