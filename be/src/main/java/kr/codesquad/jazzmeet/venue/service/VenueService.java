package kr.codesquad.jazzmeet.venue.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import kr.codesquad.jazzmeet.venue.dto.VenueSearch;
import kr.codesquad.jazzmeet.venue.dto.response.NearbyVenueResponse;
import kr.codesquad.jazzmeet.venue.dto.response.VenueAutocompleteResponse;
import kr.codesquad.jazzmeet.venue.dto.response.VenuePinsBySearchResponse;
import kr.codesquad.jazzmeet.venue.dto.response.VenueSearchResponse;
import kr.codesquad.jazzmeet.venue.entity.Venue;
import kr.codesquad.jazzmeet.venue.mapper.VenueMapper;
import kr.codesquad.jazzmeet.venue.repository.VenueQueryRepository;
import kr.codesquad.jazzmeet.venue.repository.VenueRepository;
import kr.codesquad.jazzmeet.venue.vo.NearbyVenue;
import kr.codesquad.jazzmeet.venue.vo.VenuePinsByWord;
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

	public List<VenuePinsBySearchResponse> findVenuePins(String word) {
		List<VenuePinsByWord> venues = venueQueryRepository.findVenuePinsByWord(word);

		return venues.stream()
			.map(VenueMapper.INSTANCE::toVenuePinsBySearchResponse)
			.toList();
	}

	public VenueSearchResponse searchVenueList(String word, Pageable pageable, LocalDateTime todayStartTime,
		LocalDateTime todayEndTime) {
		// TODO: default page, default size, maxPage 검증하기
		// querydsl을 사용하여 조회. date는 있을 수도(필터), 없을 수도(필터 초기화) 있기 때문에 동적 쿼리 적용해야 하기 때문.
		if (word == "" || word == null) {
			return VenueSearchResponse.emptyVenues();
		}

		int venueCount = venueQueryRepository.countSearchVenueList(word).intValue();
		int currentPage = pageable.getPageNumber();
		int maxPage = calculateMaxPage(venueCount, pageable);

		List<VenueSearch> venueSearch = venueQueryRepository
			.searchVenueList(word, pageable, todayStartTime, todayEndTime)
			.stream()
			.map(VenueMapper.INSTANCE::toVenueSearch)
			.toList();

		return VenueMapper.INSTANCE.toVenueSearchResponse(venueSearch, venueCount, currentPage, maxPage);
	}

	private int calculateMaxPage(int venueCount, Pageable pageable) {
		int size = pageable.getPageSize();
		int maxPage = venueCount / size;
		if (maxPage == 0 || venueCount % size != 0) {
			maxPage += 1;
		}
		return maxPage;
	}
}
