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
import kr.codesquad.jazzmeet.venue.dto.response.VenuePinsResponse;
import kr.codesquad.jazzmeet.venue.entity.Venue;
import kr.codesquad.jazzmeet.venue.repository.VenueRepository;
import kr.codesquad.jazzmeet.venue.util.VenueTestUtil;
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
			.thumbnailUrl("thumbnail.url")
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

	@DisplayName("이름에 검색어가 포함되는 공연장의 위치 데이터 목록을 조회한다.")
	@Test
	public void findVenuesPinsByWordInName() throws Exception {
		//given
		String word = "부기우기";
		Point point = VenueUtil.createPoint(123.11111, 123.123123);
		Venue venue1 = VenueTestUtil.createVenues("부기우기", "서울 용산구 회나무로 21 2층", point);
		Venue venue2 = VenueTestUtil.createVenues("Entry55", "서울 동작구 동작대로1길 18 B-102", point);
		venueRepository.saveAll(List.of(venue1, venue2));

		//when
		List<VenuePinsResponse> venuePinsList = venueService.findVenuePinsBySearch(word);

		//then
		assertThat(venuePinsList).hasSize(1)
			.extracting("name", "latitude", "longitude")
			.contains(tuple("부기우기", 123.11111, 123.123123));
	}

	@DisplayName("주소에 검색어가 포함되는 공연장의 위치 데이터 목록을 조회한다.")
	@Test
	public void findVenuesPinsByWordInAddress() throws Exception {
		//given
		String word = "서울";
		Point point = VenueUtil.createPoint(123.11111, 123.123123);
		Venue venue1 = VenueTestUtil.createVenues("부기우기", "서울 용산구 회나무로 21 2층", point);
		Venue venue2 = VenueTestUtil.createVenues("Entry55", "서울 동작구 동작대로1길 18 B-102", point);
		venueRepository.saveAll(List.of(venue1, venue2));

		//when
		List<VenuePinsResponse> venuePinsList = venueService.findVenuePinsBySearch(word);

		//then
		assertThat(venuePinsList).hasSize(2)
			.extracting("name", "latitude", "longitude")
			.contains(
				tuple("부기우기", 123.11111, 123.123123),
				tuple("Entry55", 123.11111, 123.123123));
	}

	@DisplayName("검색어가 NULL이면 빈 배열을 응답한다.")
	@Test
	public void findVenuesByWordIsNull() throws Exception {
		//given
		String word = null;
		Point point = VenueUtil.createPoint(123.11111, 123.123123);
		Venue venue1 = VenueTestUtil.createVenues("부기우기", "서울 용산구 회나무로 21 2층", point);
		Venue venue2 = VenueTestUtil.createVenues("Entry55", "서울 동작구 동작대로1길 18 B-102", point);
		venueRepository.saveAll(List.of(venue1, venue2));

		//when
		List<VenuePinsResponse> venuePinsList = venueService.findVenuePinsBySearch(word);

		//then
		assertThat(venuePinsList).hasSize(0);
	}

	@DisplayName("위치 정보 4개가 주어지면 해당 범위에 속하는 공연장의 위치 정보 목록을 조회한다.")
	@Test
	public void findVenuesByLocation() throws Exception {
	    //given
		Double lowLatitude = 37.51387497068088 ;
		Double highLatitude = 37.61077342780979 ;
		Double lowLongitude = 126.9293615244093 ;
		Double highLongitude = 127.10246683663273 ;

		Venue venue1 = VenueTestUtil.createVenues("부기우기", "서울 용산구 회나무로 21 2층", VenueUtil.createPoint(37.52387497068088, 126.9294615244093));
		Venue venue2 = VenueTestUtil.createVenues("Entry55", "서울 동작구 동작대로1길 18 B-102", VenueUtil.createPoint(37.53387497068088, 126.9394615244093));
		Venue venue3 = VenueTestUtil.createVenues("러스틱 재즈", "서울 마포구 망원로 74 지하", VenueUtil.createPoint(38.0, 128.0)); // 범위 벗어남
		venueRepository.saveAll(List.of(venue1, venue2, venue3));

	    //when
		List<VenuePinsResponse> venuePins = venueService.findVenuePinsByLocation(lowLatitude, highLatitude, lowLongitude, highLongitude);

	    //then
		assertThat(venuePins).hasSize(2)
			.extracting("name")
			.contains("부기우기", "Entry55");
	}

	@DisplayName("위치 정보 4개가 주어지면 해당 범위에 속하지 않는 공연장의 위치 정보 목록은 조회되지 않는다.")
	@Test
	public void findVenuesOutsideLocation() throws Exception {
		//given
		Double lowLatitude = 37.51387497068088 ;
		Double highLatitude = 37.61077342780979 ;
		Double lowLongitude = 126.9293615244093 ;
		Double highLongitude = 127.10246683663273 ;

		Venue venue1 = VenueTestUtil.createVenues("부기우기", "서울 용산구 회나무로 21 2층", VenueUtil.createPoint(36.5387497068088, 125.9294615244093));
		Venue venue2 = VenueTestUtil.createVenues("Entry55", "서울 동작구 동작대로1길 18 B-102", VenueUtil.createPoint(38.53387497068088, 128.9394615244093));
		venueRepository.saveAll(List.of(venue1, venue2));

		//when
		List<VenuePinsResponse> venuePins = venueService.findVenuePinsByLocation(lowLatitude, highLatitude, lowLongitude, highLongitude);

		//then
		assertThat(venuePins).hasSize(0);
	}

	@DisplayName("위치 정보 4개가 주어지면 해당 경계에 위치하는 공연장 위치 정보 목록은 조회되지 않는다.")
	@Test
	public void findVenuesSameLocation() throws Exception {
		//given
		Double lowLatitude = 37.51387497068088 ;
		Double highLatitude = 37.61077342780979 ;
		Double lowLongitude = 126.9293615244093 ;
		Double highLongitude = 127.10246683663273 ;

		Venue venue1 = VenueTestUtil.createVenues("부기우기", "서울 용산구 회나무로 21 2층", VenueUtil.createPoint(lowLatitude, highLongitude)); // 남동
		Venue venue2 = VenueTestUtil.createVenues("Entry55", "서울 동작구 동작대로1길 18 B-102", VenueUtil.createPoint(highLatitude, lowLongitude)); // 북서
		venueRepository.saveAll(List.of(venue1, venue2));

		//when
		List<VenuePinsResponse> venuePins = venueService.findVenuePinsByLocation(lowLatitude, highLatitude, lowLongitude, highLongitude);

		//then
		assertThat(venuePins).hasSize(0);
	}

	@DisplayName("위치 정보 4개 중 하나라도 값이 Null이면 빈 배열을 응답한다.")
	@Test
	public void findVenuesWhenLocationNull() throws Exception {
		//given
		Double lowLatitude = 37.51387497068088 ;
		Double highLatitude = 37.61077342780979 ;
		Double lowLongitude = 126.9293615244093 ;
		Double highLongitude = null ;

		Venue venue1 = VenueTestUtil.createVenues("부기우기", "서울 용산구 회나무로 21 2층", VenueUtil.createPoint(37.52387497068088, 126.9294615244093));
		Venue venue2 = VenueTestUtil.createVenues("Entry55", "서울 동작구 동작대로1길 18 B-102", VenueUtil.createPoint(37.53387497068088, 126.9394615244093));
		Venue venue3 = VenueTestUtil.createVenues("러스틱 재즈", "서울 마포구 망원로 74 지하", VenueUtil.createPoint(38.0, 128.0)); // 범위 벗어남
		venueRepository.saveAll(List.of(venue1, venue2, venue3));

		//when
		List<VenuePinsResponse> venuePins = venueService.findVenuePinsByLocation(lowLatitude, highLatitude, lowLongitude, highLongitude);

		//then
		assertThat(venuePins).hasSize(0)
			.isInstanceOf(List.class);
	}

}
