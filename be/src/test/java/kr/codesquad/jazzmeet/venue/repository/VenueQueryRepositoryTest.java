package kr.codesquad.jazzmeet.venue.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import kr.codesquad.jazzmeet.IntegrationTestSupport;
import kr.codesquad.jazzmeet.venue.entity.Venue;
import kr.codesquad.jazzmeet.venue.util.VenueTestUtil;
import kr.codesquad.jazzmeet.venue.util.VenueUtil;
import kr.codesquad.jazzmeet.venue.vo.VenuePins;

@Transactional
class VenueQueryRepositoryTest extends IntegrationTestSupport {

	@Autowired
	VenueQueryRepository venueQueryRepository;

	@Autowired
	VenueRepository venueRepository;

	@DisplayName("이름에 검색어가 포함되어 있는 공연장 정보 리스트를 조회한다.")
	@Test
	public void findVenuesByWordInName() throws Exception {
		//given
		String word = "부기우기";
		Point point = VenueUtil.createPoint(111.111, 222.222);
		Venue venue1 = VenueTestUtil.createVenues("부기우기", "서울 용산구 회나무로 21 2층", point);
		Venue venue2 = VenueTestUtil.createVenues("entry55", "서울 동작구 동작대로1길 18 B-102", point);
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
	public void findVenuesByWordInAddress() throws Exception {
		//given
		String word = "서울";
		Point point = VenueUtil.createPoint(111.111, 222.222);
		Venue venue1 = VenueTestUtil.createVenues("부기우기", "서울 용산구 회나무로 21 2층", point);
		Venue venue2 = VenueTestUtil.createVenues("entry55", "서울 동작구 동작대로1길 18 B-102", point);
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
	public void findVenuesInRange() throws Exception {
		//given
		Double lowLatitude = 37.51387497068088 ;
		Double highLatitude = 37.61077342780979 ;
		Double lowLongitude = 126.9293615244093 ;
		Double highLongitude = 127.10246683663273 ;

		Polygon range = VenueUtil.createRange(lowLatitude, highLatitude, lowLongitude, highLongitude);

		Venue venue1 = VenueTestUtil.createVenues("부기우기", "서울 용산구 회나무로 21 2층", VenueUtil.createPoint(37.52387497068088, 126.9394615244093));
		Venue venue2 = VenueTestUtil.createVenues("Entry55", "서울 동작구 동작대로1길 18 B-102", VenueUtil.createPoint(37.53387497068088, 126.9494615244093));
		Venue venue3 = VenueTestUtil.createVenues("러스틱 재즈", "서울 마포구 망원로 74 지하", VenueUtil.createPoint(38.0, 128.0)); // 범위 벗어남
		venueRepository.saveAll(List.of(venue1, venue2, venue3));

		//when
		List<VenuePins> venuePins = venueQueryRepository.findVenuePinsByLocation(range);

		//then
		assertThat(venuePins).hasSize(2)
			.extracting("name")
			.contains("부기우기", "Entry55");
	}

}