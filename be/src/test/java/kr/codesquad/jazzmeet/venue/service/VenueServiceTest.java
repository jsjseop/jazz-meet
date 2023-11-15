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
import kr.codesquad.jazzmeet.fixture.ImageFixture;
import kr.codesquad.jazzmeet.fixture.VenueFixture;
import kr.codesquad.jazzmeet.global.error.CustomException;
import kr.codesquad.jazzmeet.image.entity.Image;
import kr.codesquad.jazzmeet.image.repository.ImageRepository;
import kr.codesquad.jazzmeet.venue.dto.VenueSearch;
import kr.codesquad.jazzmeet.venue.dto.request.RangeCoordinatesRequest;
import kr.codesquad.jazzmeet.venue.dto.response.NearbyVenueResponse;
import kr.codesquad.jazzmeet.venue.dto.response.VenueAutocompleteResponse;
import kr.codesquad.jazzmeet.venue.dto.response.VenueDetailResponse;
import kr.codesquad.jazzmeet.venue.dto.response.VenuePinsResponse;
import kr.codesquad.jazzmeet.venue.dto.response.VenueSearchResponse;
import kr.codesquad.jazzmeet.venue.entity.Venue;
import kr.codesquad.jazzmeet.venue.entity.VenueImage;
import kr.codesquad.jazzmeet.venue.repository.VenueImageRepository;
import kr.codesquad.jazzmeet.venue.repository.VenueRepository;
import kr.codesquad.jazzmeet.venue.util.LocationUtil;

class VenueServiceTest extends IntegrationTestSupport {

	// 기본 위치 (서울시청)
	private static final Point DEFAULT_POINT = LocationUtil.createPoint(37.56671605441306, 126.97849382312168);

	@Autowired
	VenueService venueService;

	@Autowired
	VenueRepository venueRepository;

	@Autowired
	VenueImageRepository venueImageRepository;

	@Autowired
	ImageRepository imageRepository;

	@AfterEach
	void dbClean() {
		venueImageRepository.deleteAllInBatch();
		venueRepository.deleteAllInBatch();
		imageRepository.deleteAllInBatch();
	}

	@Test
	@DisplayName("검색어를 입력하면 공연장 이름에 검색어를 포함하는 공연장 목록을 조회한다.")
	void searchVenueNameAutocompleteList() {
		// given
		String word = "블루";
		Venue venue = VenueFixture.createVenue("플랫나인", "서울시 강남구", DEFAULT_POINT);
		Venue venue2 = VenueFixture.createVenue("블루밍", "서울시 서초구", DEFAULT_POINT);

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
		Venue venue = VenueFixture.createVenue("플랫나인", "서울시 강남구", DEFAULT_POINT);
		Venue venue2 = VenueFixture.createVenue("블루밍", "서울시 서초구", DEFAULT_POINT);

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
		Venue venue = VenueFixture.createVenue("플랫나인", "서울시 강남구", DEFAULT_POINT);
		Venue venue2 = VenueFixture.createVenue("블루밍", "서울시 서초구", DEFAULT_POINT);

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

		Point pointA = LocationUtil.createPoint(37.50049856339995, 127.0249505634053);
		Point pointB = LocationUtil.createPoint(37.5014268697288, 127.03302845194163);
		Point pointC = LocationUtil.createPoint(37.49907417387371, 127.02848692360855);

		Venue venueA = VenueFixture.createVenue("공연장A", "주소", pointA);
		Venue venueB = VenueFixture.createVenue("공연장B", "주소", pointB);
		Venue venueC = VenueFixture.createVenue("공연장C", "주소", pointC);

		venueRepository.saveAll(List.of(venueA, venueB, venueC));

		// when
		List<NearbyVenueResponse> nearByVenues = venueService.findNearByVenues(latitude, longitude);

		// then
		Assertions.assertAll(
			() -> assertThat(nearByVenues.get(0).name()).isEqualTo("공연장A"),
			() -> assertThat(nearByVenues.get(1).name()).isEqualTo("공연장C"),
			() -> assertThat(nearByVenues.get(2).name()).isEqualTo("공연장B")
		);
	}

	@DisplayName("이름에 검색어가 포함되는 공연장의 위치 데이터 목록을 조회한다.")
	@Test
	void findVenuesPinsByWordInName() throws Exception {
		//given
		String word = "부기우기";
		Point point = LocationUtil.createPoint(123.11111, 123.123123);
		Venue venue1 = VenueFixture.createVenue("부기우기", "서울 용산구 회나무로 21 2층", point);
		Venue venue2 = VenueFixture.createVenue("Entry55", "서울 동작구 동작대로1길 18 B-102", point);
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
	void findVenuesPinsByWordInAddress() throws Exception {
		//given
		String word = "서울";
		Point point = LocationUtil.createPoint(123.11111, 123.123123);
		Venue venue1 = VenueFixture.createVenue("부기우기", "서울 용산구 회나무로 21 2층", point);
		Venue venue2 = VenueFixture.createVenue("Entry55", "서울 동작구 동작대로1길 18 B-102", point);
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
	void findVenuesByWordIsNull() throws Exception {
		//given
		String word = null;
		Point point = LocationUtil.createPoint(123.11111, 123.123123);
		Venue venue1 = VenueFixture.createVenue("부기우기", "서울 용산구 회나무로 21 2층", point);
		Venue venue2 = VenueFixture.createVenue("Entry55", "서울 동작구 동작대로1길 18 B-102", point);
		venueRepository.saveAll(List.of(venue1, venue2));

		//when
		List<VenuePinsResponse> venuePinsList = venueService.findVenuePinsBySearch(word);

		//then
		assertThat(venuePinsList).hasSize(0);
	}

	@DisplayName("위치 정보 4개가 주어지면 해당 범위에 속하는 공연장의 위치 정보 목록을 조회한다.")
	@Test
	void findVenuesByLocation() throws Exception {
		//given
		Double lowLatitude = 37.51387497068088;
		Double highLatitude = 37.61077342780979;
		Double lowLongitude = 126.9293615244093;
		Double highLongitude = 127.10246683663273;
		RangeCoordinatesRequest rangeCoordinatesRequest = VenueFixture.createRangeCoordinatesRequest(
			lowLatitude, highLatitude, lowLongitude, highLongitude);

		Venue venue1 = VenueFixture.createVenue("부기우기", "서울 용산구 회나무로 21 2층",
			LocationUtil.createPoint(37.52387497068088, 126.9294615244093));
		Venue venue2 = VenueFixture.createVenue("Entry55", "서울 동작구 동작대로1길 18 B-102",
			LocationUtil.createPoint(37.53387497068088, 126.9394615244093));
		Venue venue3 = VenueFixture.createVenue("러스틱 재즈", "서울 마포구 망원로 74 지하",
			LocationUtil.createPoint(38.0, 128.0)); // 범위 벗어남
		venueRepository.saveAll(List.of(venue1, venue2, venue3));

		//when
		List<VenuePinsResponse> venuePins = venueService.findVenuePinsByLocation(rangeCoordinatesRequest);

		//then
		assertThat(venuePins).hasSize(2)
			.extracting("name")
			.contains("부기우기", "Entry55");
	}

	@DisplayName("위치 정보 4개가 주어지면 해당 범위에 속하지 않는 공연장의 위치 정보 목록은 조회되지 않는다.")
	@Test
	void findVenuesOutsideLocation() throws Exception {
		//given
		Double lowLatitude = 37.51387497068088;
		Double highLatitude = 37.61077342780979;
		Double lowLongitude = 126.9293615244093;
		Double highLongitude = 127.10246683663273;
		RangeCoordinatesRequest rangeCoordinatesRequest = VenueFixture.createRangeCoordinatesRequest(
			lowLatitude, highLatitude, lowLongitude, highLongitude);

		Venue venue1 = VenueFixture.createVenue("부기우기", "서울 용산구 회나무로 21 2층",
			LocationUtil.createPoint(36.5387497068088, 125.9294615244093));
		Venue venue2 = VenueFixture.createVenue("Entry55", "서울 동작구 동작대로1길 18 B-102",
			LocationUtil.createPoint(38.53387497068088, 128.9394615244093));
		venueRepository.saveAll(List.of(venue1, venue2));

		//when
		List<VenuePinsResponse> venuePins = venueService.findVenuePinsByLocation(rangeCoordinatesRequest);

		//then
		assertThat(venuePins).hasSize(0);
	}

	@DisplayName("위치 정보 4개가 주어지면 해당 경계에 위치하는 공연장 위치 정보 목록은 조회되지 않는다.")
	@Test
	void findVenuesSameLocation() throws Exception {
		//given
		Double lowLatitude = 37.51387497068088;
		Double highLatitude = 37.61077342780979;
		Double lowLongitude = 126.9293615244093;
		Double highLongitude = 127.10246683663273;
		RangeCoordinatesRequest rangeCoordinatesRequest = VenueFixture.createRangeCoordinatesRequest(
			lowLatitude, highLatitude, lowLongitude, highLongitude);

		Venue venue1 = VenueFixture.createVenue("부기우기", "서울 용산구 회나무로 21 2층",
			LocationUtil.createPoint(lowLatitude, highLongitude)); // 남동
		Venue venue2 = VenueFixture.createVenue("Entry55", "서울 동작구 동작대로1길 18 B-102",
			LocationUtil.createPoint(highLatitude, lowLongitude)); // 북서
		venueRepository.saveAll(List.of(venue1, venue2));

		//when
		List<VenuePinsResponse> venuePins = venueService.findVenuePinsByLocation(rangeCoordinatesRequest);

		//then
		assertThat(venuePins).hasSize(0);
	}

	@DisplayName("위치 정보 4개 중 하나라도 값이 Null이면 빈 배열을 응답한다.")
	@Test
	void findVenuesWhenLocationNull() throws Exception {
		//given
		Double lowLatitude = 37.51387497068088;
		Double highLatitude = 37.61077342780979;
		Double lowLongitude = 126.9293615244093;
		Double highLongitude = null;
		RangeCoordinatesRequest rangeCoordinatesRequest = VenueFixture.createRangeCoordinatesRequest(
			lowLatitude, highLatitude, lowLongitude, highLongitude);

		Venue venue1 = VenueFixture.createVenue("부기우기", "서울 용산구 회나무로 21 2층",
			LocationUtil.createPoint(37.52387497068088, 126.9294615244093));
		Venue venue2 = VenueFixture.createVenue("Entry55", "서울 동작구 동작대로1길 18 B-102",
			LocationUtil.createPoint(37.53387497068088, 126.9394615244093));
		Venue venue3 = VenueFixture.createVenue("러스틱 재즈", "서울 마포구 망원로 74 지하",
			LocationUtil.createPoint(38.0, 128.0)); // 범위 벗어남
		venueRepository.saveAll(List.of(venue1, venue2, venue3));

		//when
		List<VenuePinsResponse> venuePins = venueService.findVenuePinsByLocation(rangeCoordinatesRequest);

		//then
		assertThat(venuePins).hasSize(0)
			.isInstanceOf(List.class);
	}

	@Test
	@DisplayName("위치 정보 4개의 범위에 포함되고 page에 해당하는 공연장 목록을 조회한다.")
	void findVenuesByLocationMap() {
		// given
		Double lowLatitude = 37.54872034392914;
		Double highLatitude = 37.56412598864679;
		Double lowLongitude = 126.91146553944996;
		Double highLongitude = 126.94298838861823;
		RangeCoordinatesRequest rangeCoordinatesRequest = VenueFixture.createRangeCoordinatesRequest(
			lowLatitude, highLatitude, lowLongitude, highLongitude);
		int page = 1;

		Point point1 = LocationUtil.createPoint(37.558531649528895, 126.91891927086303);
		Point point2 = LocationUtil.createPoint(37.5560565982576, 126.930179961597);
		Point point3 = LocationUtil.createPoint(37.5502406352943, 126.9229031896536);

		Venue venue1 = VenueFixture.createVenue("연남5701", "서울 마포구 동교로23길 64 지하", point1);
		Venue venue2 = VenueFixture.createVenue("숲길", "서울 마포구 와우산로37길 11", point2);
		Venue venue3 = VenueFixture.createVenue("클럽에반스", "서울 마포구 와우산로 63 2층", point3);

		venueRepository.saveAll(List.of(venue1, venue2, venue3));

		// when
		VenueSearchResponse venuesByLocation = venueService.findVenuesByLocation(rangeCoordinatesRequest, page);

		// then
		Assertions.assertAll(
			() -> assertThat(venuesByLocation.venues()).hasSize(3)
				.extracting("name")
				.containsExactly("연남5701", "숲길", "클럽에반스"),
			() -> assertThat(venuesByLocation.currentPage()).isEqualTo(page),
			() -> assertThat(venuesByLocation.maxPage()).isEqualTo(page)
		);
	}

	@Test
	@DisplayName("위치 정보 4개 중 null 값이 존재하면 공연장 목록은 빈 배열로 응답한다.")
	void findVenuesByLocationNull() {
		// given
		Double lowLatitude = null;
		Double highLatitude = 37.56412598864679;
		Double lowLongitude = 126.91146553944996;
		Double highLongitude = null;
		RangeCoordinatesRequest rangeCoordinatesRequest = VenueFixture.createRangeCoordinatesRequest(
			lowLatitude, highLatitude, lowLongitude, highLongitude);
		int page = 1;

		Point point1 = LocationUtil.createPoint(37.558531649528895, 126.91891927086303);
		Point point2 = LocationUtil.createPoint(37.5560565982576, 126.930179961597);
		Point point3 = LocationUtil.createPoint(37.5502406352943, 126.9229031896536);

		Venue venue1 = VenueFixture.createVenue("연남5701", "서울 마포구 동교로23길 64 지하", point1);
		Venue venue2 = VenueFixture.createVenue("숲길", "서울 마포구 와우산로37길 11", point2);
		Venue venue3 = VenueFixture.createVenue("클럽에반스", "서울 마포구 와우산로 63 2층", point3);

		venueRepository.saveAll(List.of(venue1, venue2, venue3));

		// when
		VenueSearchResponse venuesByLocation = venueService.findVenuesByLocation(rangeCoordinatesRequest, page);

		// then
		assertThat(venuesByLocation.venues()).hasSize(0);
	}

	@Test
	@DisplayName("공연장 ID에 해당하는 공연장 정보를 목록으로 응답한다.")
	void findVenueSearchById() {
		// given
		Venue venue = VenueFixture.createVenue("공연장", "주소", DEFAULT_POINT);

		Venue savedVenue = venueRepository.save(venue);
		Long venueId = savedVenue.getId();

		// when
		VenueSearchResponse venueSearch = venueService.findVenueSearchById(venueId);

		// then
		assertThat(venueSearch.venues()).hasSize(1)
			.extracting("id")
			.containsExactly(venueId);
	}

	@Test
	@DisplayName("존재하지 않는 공연장 ID로 조회하면 빈 배열로 응답한다.")
	void findVenueSearchByWrongId() {
		// given
		Long venueId = 2L;

		// when
		VenueSearchResponse venueSearch = venueService.findVenueSearchById(venueId);

		// then
		assertThat(venueSearch.venues()).isEmpty();
	}

	@Test
	@DisplayName("검색어에 해당되는 공연장 주소의 목록과 개수를 반환한다.")
	void searchVenueListContainsWordInAddress() {
		// given - 검사 할 공연장 저장
		Venue venue1 = VenueFixture.createVenue("블루밍 재즈바", "서울 강남구 테헤란로19길 21");
		Venue venue2 = VenueFixture.createVenue("플랫나인", "서울 서초구 강남대로65길 10");
		Venue venue3 = VenueFixture.createVenue("entry55", "서울 동작구 동작대로1길");

		List<Venue> savedVenues = venueRepository.saveAll(List.of(venue1, venue2, venue3));

		// when
		String word = "강남";
		int page = 1;

		VenueSearchResponse venueSearchResponse = venueService.searchVenueList(word, page);

		// then
		assertThat(venueSearchResponse.totalCount()).isEqualTo(2);
		assertThat(venueSearchResponse.currentPage()).isEqualTo(page);
		assertThat(venueSearchResponse.maxPage()).isEqualTo(1);
		assertThat(venueSearchResponse.venues()).extracting(VenueSearch::address)
			.doesNotContain(venue3.getRoadNameAddress())
			.contains(venue1.getRoadNameAddress())
			.contains(venue2.getRoadNameAddress());
	}

	@Test
	@DisplayName("검색어에 해당되는 공연장 이름의 목록과 개수를 반환한다.")
	void searchVenueListContainsWordInName() {
		// given - 검사 할 공연장 저장
		Venue venue1 = VenueFixture.createVenue("블루밍 재즈바", "서울 강남구 테헤란로19길 21");
		Venue venue2 = VenueFixture.createVenue("올댓재즈", "서울 용산구 이태원로 216");
		Venue venue3 = VenueFixture.createVenue("entry55", "서울 동작구 동작대로1길");

		venueRepository.saveAll(List.of(venue1, venue2, venue3));

		// when
		String word = "재즈";
		int page = 1;

		VenueSearchResponse venueSearchResponse = venueService.searchVenueList(word, page);

		// then
		assertThat(venueSearchResponse.totalCount()).isEqualTo(2);
		assertThat(venueSearchResponse.currentPage()).isEqualTo(page);
		assertThat(venueSearchResponse.venues()).extracting(VenueSearch::name)
			.doesNotContain(venue3.getName())
			.contains(venue1.getName())
			.contains(venue2.getName());
	}

	@DisplayName("venue id가 주어지면 해당하는 공연장의 상세 정보를 조회한다.")
	@Test
	void findVenue() throws Exception {
		//given
		Venue venue = VenueFixture.createVenue("부기우기", "서울 용산구 회나무로 21 2층",
			LocationUtil.createPoint(37.52387497068088, 126.9294615244093));

		Image image1 = ImageFixture.createImage("image1.url");
		Image image2 = ImageFixture.createImage("image2.url");

		imageRepository.saveAll(List.of(image1, image2));

		VenueImage venueImage1 = VenueFixture.createVenueImage(venue, image1, 1L);
		VenueImage venueImage2 = VenueFixture.createVenueImage(venue, image2, 2L);

		venue.addVenueImage(venueImage1);
		venue.addVenueImage(venueImage2);

		venueRepository.save(venue);

		Long venueId = venue.getId();

		//when
		VenueDetailResponse venueResponse = venueService.findVenue(venueId);

		//then
		assertThat(venueResponse)
			.extracting("name")
			.isEqualTo("부기우기");
	}

	@DisplayName("venue id에 해당하는 공연장이 없으면 예외를 응답한다.")
	@Test
	void findVenueWhenNotExistVenue() throws Exception {
		//given
		Long venueId = -1L;
		Venue venue = VenueFixture.createVenue("부기우기", "서울 용산구 회나무로 21 2층",
			LocationUtil.createPoint(37.52387497068088, 126.9294615244093));

		Image image1 = ImageFixture.createImage("image1.url");
		Image image2 = ImageFixture.createImage("image2.url");

		imageRepository.saveAll(List.of(image1, image2));

		VenueImage venueImage1 = VenueFixture.createVenueImage(venue, image1, 1L);
		VenueImage venueImage2 = VenueFixture.createVenueImage(venue, image2, 2L);

		venue.addVenueImage(venueImage1);
		venue.addVenueImage(venueImage2);

		venueRepository.save(venue);

		//when //then
		assertThatThrownBy(() -> venueService.findVenue(venueId))
			.isInstanceOf(CustomException.class);
	}

}
