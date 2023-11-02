package kr.codesquad.jazzmeet.venue.service;

import java.util.List;

import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import kr.codesquad.jazzmeet.venue.dto.VenueSearch;
import kr.codesquad.jazzmeet.venue.dto.response.NearbyVenueResponse;
import kr.codesquad.jazzmeet.venue.dto.response.VenueAutocompleteResponse;
import kr.codesquad.jazzmeet.venue.dto.response.VenuePinsResponse;
import kr.codesquad.jazzmeet.venue.dto.response.VenueSearchResponse;
import kr.codesquad.jazzmeet.venue.entity.Venue;
import kr.codesquad.jazzmeet.venue.mapper.VenueMapper;
import kr.codesquad.jazzmeet.venue.repository.VenueQueryRepository;
import kr.codesquad.jazzmeet.venue.repository.VenueRepository;
import kr.codesquad.jazzmeet.venue.util.VenueUtil;
import kr.codesquad.jazzmeet.venue.vo.NearbyVenue;
import kr.codesquad.jazzmeet.venue.vo.VenuePins;
import kr.codesquad.jazzmeet.venue.vo.VenueSearchData;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class VenueService {

	private static final int PAGE_NUMBER_OFFSET = 1; // 페이지를 1부터 시작하게 하기 위한 offset
	private static final int PAGE_SIZE = 10;

	private final VenueRepository venueRepository;
	private final VenueQueryRepository venueQueryRepository;

	public List<VenueAutocompleteResponse> searchAutocompleteList(String word) {
		// word가 "" 이면 공연장 목록 전부 조회
		List<Venue> venues = venueRepository.findTop10ByNameContainingOrRoadNameAddressContaining(word,
			word);

		return venues.stream()
			.map(VenueMapper.INSTANCE::toVenueAutocompleteResponse)
			.toList();
	}

	public List<NearbyVenueResponse> findNearByVenues(Double latitude, Double longitude) {
		if (validateCoordinates(latitude, longitude)) {
			return List.of();
		}

		Point point = VenueUtil.createPoint(latitude, longitude);
		List<NearbyVenue> venues = venueQueryRepository.findNearbyVenuesByLocation(point);

		return venues.stream()
			.map(VenueMapper.INSTANCE::toNearByVenueResponse)
			.toList();
	}

	public List<VenuePinsResponse> findVenuePinsBySearch(String word) {
		if (word == null) {
			return List.of();
		}

		List<VenuePins> venues = venueQueryRepository.findVenuePinsByWord(word);

		return venues.stream()
			.map(VenueMapper.INSTANCE::toVenuePinsBySearchResponse)
			.toList();
	}

	public List<VenuePinsResponse> findVenuePinsByLocation(Double lowLatitude, Double highLatitude, Double lowLongitude,
		Double highLongitude) {
		if (validateCoordinates(lowLatitude, highLatitude, lowLongitude, highLongitude)) {
			return List.of();
		}

		Polygon range = VenueUtil.createRange(lowLatitude, highLatitude, lowLongitude, highLongitude);

		List<VenuePins> venues = venueQueryRepository.findVenuePinsByLocation(range);

		return venues.stream()
			.map(VenueMapper.INSTANCE::toVenuePinsBySearchResponse)
			.toList();
	}

	public VenueSearchResponse findVenuesByLocation(Double lowLatitude, Double highLatitude,
		Double lowLongitude, Double highLongitude, int page) {
		if (validateCoordinates(lowLatitude, highLatitude, lowLongitude, highLongitude)) {
			return VenueSearchResponse.builder()
				.venues(List.of())
				.build();
		}

		Polygon range = VenueUtil.createRange(lowLatitude, highLatitude, lowLongitude, highLongitude);
		PageRequest pageRequest = PageRequest.of(page - PAGE_NUMBER_OFFSET, PAGE_SIZE);
		Page<VenueSearchData> venuesByLocation = venueQueryRepository.findVenuesByLocation(range, pageRequest);

		List<VenueSearch> venueSearchList = venuesByLocation.getContent()
			.stream()
			.map(VenueMapper.INSTANCE::toVenueSearch)
			.toList();

		// Todo: Mapper로 변경
		return VenueSearchResponse.builder()
			.venues(venueSearchList)
			.venueCount(venuesByLocation.getTotalElements())
			.currentPage(venuesByLocation.getNumber() + PAGE_NUMBER_OFFSET)
			.maxPage(venuesByLocation.getTotalPages())
			.build();
	}

	private boolean validateCoordinates(Double latitude, Double longitude) {
		return latitude == null || longitude == null;
	}

	private boolean validateCoordinates(Double lowLatitude, Double highLatitude, Double lowLongitude,
		Double highLongitude) {
		return lowLatitude == null || highLatitude == null || lowLongitude == null || highLongitude == null;
	}
}
