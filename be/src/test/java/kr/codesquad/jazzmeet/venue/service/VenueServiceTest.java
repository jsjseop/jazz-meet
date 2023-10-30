package kr.codesquad.jazzmeet.venue.service;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;

import kr.codesquad.jazzmeet.IntegrationTestSupport;
import kr.codesquad.jazzmeet.venue.dto.response.NearbyVenueResponse;
import kr.codesquad.jazzmeet.venue.dto.response.VenueAutocompleteResponse;
import kr.codesquad.jazzmeet.venue.entity.Venue;
import kr.codesquad.jazzmeet.venue.repository.VenueRepository;
import kr.codesquad.jazzmeet.venue.util.VenueUtil;

class VenueServiceTest extends IntegrationTestSupport {

	@Autowired
	private VenueService venueService;

	@Autowired
	private VenueRepository venueRepository;

	@AfterEach
	void dbClean() {
		venueRepository.deleteAllInBatch();
	}

	@Test
	@DisplayName("검색어를 입력하면 공연장 이름에 검색어를 포함하는 공연장 목록을 조회한다.")
	void searchVenueNameAutocompleteList() {
		// given
		String word = "블루";
		Venue venue = createVenue("플랫나인", "서울시 강남구");
		Venue venue2 = createVenue("블루밍", "서울시 서초구");

		venueRepository.save(venue);
		venueRepository.save(venue2);

		// when
		List<VenueAutocompleteResponse> foundVenues = venueService.searchAutocompleteList(word);

		// then
		int size = foundVenues.size();
		assertThat(foundVenues).filteredOn(
				venueAutocompleteResponse -> venueAutocompleteResponse.name().contains(word))
			.hasSize(size);
	}

	@Test
	@DisplayName("검색어를 입력하면 주소에 검색어를 포함하는 공연장 목록을 조회한다.")
	void searchAddressNameAutocompleteList() {
		// given
		String word = "강남";
		Venue venue = createVenue("플랫나인", "서울시 강남구");
		Venue venue2 = createVenue("블루밍", "서울시 서초구");

		venueRepository.save(venue);
		venueRepository.save(venue2);

		// when
		List<VenueAutocompleteResponse> foundVenues = venueService.searchAutocompleteList(word);

		// then
		int size = foundVenues.size();
		assertThat(foundVenues).filteredOn(
				venueAutocompleteResponse -> venueAutocompleteResponse.address().contains(word))
			.hasSize(size);
	}

	@Test
	@DisplayName("공연장 이름과 주소에 검색어를 포함하는 공연장이 없으면 목록이 조회되지 않는다.")
	void searchEmptyListAutocompleteList() {
		// given
		String word = "안양";
		Venue venue = createVenue("플랫나인", "서울시 강남구");
		Venue venue2 = createVenue("블루밍", "서울시 서초구");

		venueRepository.save(venue);
		venueRepository.save(venue2);

		// when
		List<VenueAutocompleteResponse> foundVenues = venueService.searchAutocompleteList(word);

		// then
		assertThat(foundVenues).hasSize(0);
	}

	@Test
	@DisplayName("특정 위치에서 거리가 가까운 공연장의 목록을 조회한다.")
	void findNearByVenues() {
		// given
		double latitude = 37.49824611392008;
		double longitude = 127.02463599761059;
		Venue venueA = createVenueWithLocation("공연장A", "주소", 37.50049856339995, 127.0249505634053);
		Venue venueB = createVenueWithLocation("공연장B", "주소", 37.5014268697288, 127.03302845194163);
		Venue venueC = createVenueWithLocation("공연장C", "주소", 37.49907417387371, 127.02848692360855);

		venueRepository.save(venueA);
		venueRepository.save(venueB);
		venueRepository.save(venueC);

		// when
		List<NearbyVenueResponse> nearByVenues = venueService.findNearByVenues(latitude, longitude);

		// then
		Assertions.assertAll(
			() -> assertThat(nearByVenues.get(0).name()).isEqualTo("공연장A"),
			() -> assertThat(nearByVenues.get(1).name()).isEqualTo("공연장C"),
			() -> assertThat(nearByVenues.get(2).name()).isEqualTo("공연장B")
		);
	}

	Venue createVenue(String name, String roadNameAddress) {
		Point point = VenueUtil.createPoint(123.11111, 123.123123);

		return Venue.builder()
			.name(name)
			.roadNameAddress(roadNameAddress)
			.lotNumberAddress("지번주소")
			.location(point)
			.build();
	}

	Venue createVenueWithLocation(String name, String roadNameAddress, Double latitude, Double longitude) {
		Point point = VenueUtil.createPoint(latitude, longitude);

		return Venue.builder()
			.name(name)
			.roadNameAddress(roadNameAddress)
			.lotNumberAddress("지번주소")
			.location(point)
			.build();
	}
}
