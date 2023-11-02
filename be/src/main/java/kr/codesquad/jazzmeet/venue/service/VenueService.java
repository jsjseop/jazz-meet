package kr.codesquad.jazzmeet.venue.service;

import java.time.LocalDateTime;
import java.util.List;

import org.locationtech.jts.geom.Polygon;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class VenueService {

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
		List<NearbyVenue> venues = venueQueryRepository.findNearbyVenuesByLocation(latitude, longitude);

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
		if (lowLatitude == null || highLatitude == null || lowLongitude == null || highLongitude == null) {
			return List.of();
		}

		Polygon range = VenueUtil.createRange(lowLatitude, highLatitude, lowLongitude, highLongitude);

		List<VenuePins> venues = venueQueryRepository.findVenuePinsByLocation(range);

		return venues.stream()
			.map(VenueMapper.INSTANCE::toVenuePinsBySearchResponse)
			.toList();
	}

	@Transactional(readOnly = true)
	public VenueSearchResponse searchVenueList(String word, int page, LocalDateTime todayStartTime,
		LocalDateTime todayEndTime) {
		if (word == "" || word == null) {
			return VenueSearchResponse.emptyVenues();
		}
		PageRequest pageRequest = PageRequest.of(page, 10);

		List<VenueSearch> venueSearch = venueQueryRepository
			.searchVenueList(word, pageRequest, todayStartTime, todayEndTime)
			.stream()
			.map(VenueMapper.INSTANCE::toVenueSearch)
			.toList();

		return makeVenueSearchResponse(word, pageRequest, venueSearch);
	}

	private VenueSearchResponse makeVenueSearchResponse(String word, PageRequest pageRequest,
		List<VenueSearch> venueSearch) {
		int venueCount = venueQueryRepository.countSearchVenueList(word).intValue();
		int currentPage = pageRequest.getPageNumber();
		int maxPage = calculateMaxPage(venueCount, pageRequest);

		return VenueMapper.INSTANCE.toVenueSearchResponse(venueSearch, venueCount, currentPage, maxPage);
	}

	private int calculateMaxPage(int venueCount, PageRequest pageRequest) {
		int size = pageRequest.getPageSize();
		int maxPage = venueCount / size;
		if (maxPage == 0 || venueCount % size != 0) {
			maxPage += 1;
		}
		return maxPage;
	}
}
