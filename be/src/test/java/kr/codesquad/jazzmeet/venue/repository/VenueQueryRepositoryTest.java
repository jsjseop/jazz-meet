package kr.codesquad.jazzmeet.venue.repository;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import kr.codesquad.jazzmeet.IntegrationTestSupport;
import kr.codesquad.jazzmeet.fixture.ImageFixture;
import kr.codesquad.jazzmeet.fixture.ShowFixture;
import kr.codesquad.jazzmeet.fixture.VenueFixture;
import kr.codesquad.jazzmeet.image.entity.Image;
import kr.codesquad.jazzmeet.image.repository.ImageRepository;
import kr.codesquad.jazzmeet.show.entity.Show;
import kr.codesquad.jazzmeet.show.repository.ShowRepository;
import kr.codesquad.jazzmeet.venue.entity.Venue;
import kr.codesquad.jazzmeet.venue.entity.VenueImage;
import kr.codesquad.jazzmeet.venue.util.VenueUtil;
import kr.codesquad.jazzmeet.venue.vo.VenueDetail;
import kr.codesquad.jazzmeet.venue.vo.VenuePins;
import kr.codesquad.jazzmeet.venue.vo.VenueSearchData;

@Transactional
class VenueQueryRepositoryTest extends IntegrationTestSupport {

	@Autowired
	VenueQueryRepository venueQueryRepository;

	@Autowired
	VenueRepository venueRepository;

	@Autowired
	ShowRepository showRepository;

	@Autowired
	VenueImageRepository venueImageRepository;

	@Autowired
	ImageRepository imageRepository;

	@AfterEach
	void dbClean() {
		venueImageRepository.deleteAllInBatch();
		showRepository.deleteAllInBatch();
		venueRepository.deleteAllInBatch();
	}

	@DisplayName("이름에 검색어가 포함되어 있는 공연장 정보 리스트를 조회한다.")
	@Test
	void findVenuesByWordInName() throws Exception {
		//given
		String word = "부기우기";
		Point point = VenueUtil.createPoint(111.111, 222.222);
		Venue venue1 = VenueFixture.createVenue("부기우기", "서울 용산구 회나무로 21 2층", point);
		Venue venue2 = VenueFixture.createVenue("entry55", "서울 동작구 동작대로1길 18 B-102", point);
		venueRepository.saveAll(List.of(venue1, venue2));

		//when
		List<VenuePins> result = venueQueryRepository.findVenuePinsByWord(word);

		//then
		assertThat(result).hasSize(1)
			.extracting("name", "location")
			.contains(tuple("부기우기", point));
	}

	@DisplayName("주소에 검색어가 포함되어 있는 공연장 정보 리스트를 조회한다.")
	@Test
	void findVenuesByWordInAddress() throws Exception {
		//given
		String word = "서울";
		Point point = VenueUtil.createPoint(111.111, 222.222);
		Venue venue1 = VenueFixture.createVenue("부기우기", "서울 용산구 회나무로 21 2층", point);
		Venue venue2 = VenueFixture.createVenue("entry55", "서울 동작구 동작대로1길 18 B-102", point);
		venueRepository.saveAll(List.of(venue1, venue2));

		//when
		List<VenuePins> result = venueQueryRepository.findVenuePinsByWord(word);

		//then
		assertThat(result).hasSize(2)
			.extracting("name", "location")
			.contains(
				tuple("부기우기", point),
				tuple("entry55", point));
	}

	@DisplayName("주어진 위치 정보 범위 안에 해당하는 공연장 위치 정보 목록을 조회한다.")
	@Test
	void findVenuesInRange() throws Exception {
		//given
		Double lowLatitude = 37.51387497068088;
		Double highLatitude = 37.61077342780979;
		Double lowLongitude = 126.9293615244093;
		Double highLongitude = 127.10246683663273;

		Polygon range = VenueUtil.createRange(lowLatitude, highLatitude, lowLongitude, highLongitude);

		Venue venue1 = VenueFixture.createVenue("부기우기", "서울 용산구 회나무로 21 2층",
			VenueUtil.createPoint(37.52387497068088, 126.9394615244093));
		Venue venue2 = VenueFixture.createVenue("Entry55", "서울 동작구 동작대로1길 18 B-102",
			VenueUtil.createPoint(37.53387497068088, 126.9494615244093));
		Venue venue3 = VenueFixture.createVenue("러스틱 재즈", "서울 마포구 망원로 74 지하",
			VenueUtil.createPoint(38.0, 128.0)); // 범위 벗어남
		venueRepository.saveAll(List.of(venue1, venue2, venue3));

		//when
		List<VenuePins> venuePins = venueQueryRepository.findVenuePinsByLocation(range);

		//then
		assertThat(venuePins).hasSize(2)
			.extracting("name")
			.contains("부기우기", "Entry55");
	}

	@Test
	@DisplayName("주어진 위치 정보 범위에 포함되고 입력 받은 일자에 진행되는 공연을 포함하는 공연장 목록을 조회한다.")
	void findVenuesByLocationMap() {
		// given
		Double lowLatitude = 37.54872034392914;
		Double highLatitude = 37.56412598864679;
		Double lowLongitude = 126.91146553944996;
		Double highLongitude = 126.94298838861823;
		Polygon range = VenueUtil.createRange(lowLatitude, highLatitude, lowLongitude, highLongitude);
		PageRequest pageRequest = PageRequest.of(0, 10);

		Point point1 = VenueUtil.createPoint(37.558531649528895, 126.91891927086303);
		Point point2 = VenueUtil.createPoint(37.5560565982576, 126.930179961597);

		Venue venue1 = VenueFixture.createVenue("연남5701", "서울 마포구 동교로23길 64 지하", point1);
		Venue venue2 = VenueFixture.createVenue("숲길", "서울 마포구 와우산로37길 11", point2);

		venueRepository.saveAll(List.of(venue1, venue2));

		LocalDate curDate = LocalDate.of(2023, 11, 11);

		LocalDateTime curStartTime1 = curDate.atTime(19, 0);
		LocalDateTime curEndTime1 = curDate.atTime(20, 0);
		LocalDateTime curStartTime2 = curDate.atTime(21, 0);
		LocalDateTime curEndTime2 = curDate.atTime(22, 0);
		LocalDateTime nextDayStartTime = curDate.plusDays(1).atTime(19, 0);
		LocalDateTime nextOneDayEndTime = curDate.plusDays(1).atTime(20, 0);
		Show show1 = ShowFixture.createShow("현재일 공연1", curStartTime1, curEndTime1, venue1);
		Show show2 = ShowFixture.createShow("현재일 공연2", curStartTime2, curEndTime2, venue1);
		Show show3 = ShowFixture.createShow("다음날 공연", nextDayStartTime, nextOneDayEndTime, venue1);

		showRepository.saveAll(List.of(show1, show2, show3));

		// when
		Page<VenueSearchData> venuesByLocation = venueQueryRepository.findVenuesByLocation(range, pageRequest, curDate);
		List<VenueSearchData> content = venuesByLocation.getContent();

		// then
		Assertions.assertAll(
			() -> assertThat(content).hasSize(2)
				.extracting("name")
				.containsExactly("연남5701", "숲길"),
			() -> assertThat(content.get(0).getShowInfo()).hasSize(2)
				.extracting("startTime")
				.containsExactly(curStartTime1, curStartTime2)
		);
	}

	@Test
	@DisplayName("주어진 공연장 ID에 해당하고 입력 받은 일자에 진행되는 공연을 포함하는 공연장 목록을 조회한다.")
	void findVenuesById() {
		// given
		Point point1 = VenueUtil.createPoint(37.558531649528895, 126.91891927086303);
		Point point2 = VenueUtil.createPoint(37.5560565982576, 126.930179961597);

		Venue venue1 = VenueFixture.createVenue("연남5701", "서울 마포구 동교로23길 64 지하", point1);
		Venue venue2 = VenueFixture.createVenue("숲길", "서울 마포구 와우산로37길 11", point2);

		venueRepository.saveAll(List.of(venue1, venue2));

		LocalDate curDate = LocalDate.of(2023, 11, 11);

		LocalDateTime curStartTime1 = curDate.atTime(19, 0);
		LocalDateTime curEndTime1 = curDate.atTime(20, 0);
		LocalDateTime curStartTime2 = curDate.atTime(21, 0);
		LocalDateTime curEndTime2 = curDate.atTime(22, 0);
		LocalDateTime nextDayStartTime = curDate.plusDays(1).atTime(19, 0);
		LocalDateTime nextOneDayEndTime = curDate.plusDays(1).atTime(20, 0);
		Show show1 = ShowFixture.createShow("현재일 공연1", curStartTime1, curEndTime1, venue1);
		Show show2 = ShowFixture.createShow("현재일 공연2", curStartTime2, curEndTime2, venue1);
		Show show3 = ShowFixture.createShow("다음날 공연", nextDayStartTime, nextOneDayEndTime, venue1);

		showRepository.saveAll(List.of(show1, show2, show3));

		// when
		List<VenueSearchData> venueSearch = venueQueryRepository.findVenueSearchById(venue1.getId(), curDate);

		// then
		Assertions.assertAll(
			() -> assertThat(venueSearch).hasSize(1)
				.extracting("name")
				.containsExactly("연남5701"),
			() -> assertThat(Objects.requireNonNull(venueSearch).get(0).getShowInfo()).hasSize(2)
				.extracting("startTime")
				.containsExactly(curStartTime1, curStartTime2)
		);
	}

	@DisplayName("venue id에 해당하는 공연장 정보를 조회한다.")
	@Test
	void findVenueById() throws Exception {
		//given
		Venue venue = VenueFixture.createVenue("부기우기", "서울 용산구 회나무로 21 2층",
			VenueUtil.createPoint(37.52387497068088, 126.9294615244093));

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
		VenueDetail venueDetail = venueQueryRepository.findVenue(venueId).get();

		//then
		assertThat(venueDetail)
			.extracting("name")
			.isEqualTo("부기우기");

		assertThat(venueDetail.getImages()).hasSize(2)
			.extracting("url")
			.contains("image1.url", "image2.url");
	}
}
