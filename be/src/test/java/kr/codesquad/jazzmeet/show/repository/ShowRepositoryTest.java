package kr.codesquad.jazzmeet.show.repository;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import kr.codesquad.jazzmeet.IntegrationTestSupport;
import kr.codesquad.jazzmeet.fixture.ImageFixture;
import kr.codesquad.jazzmeet.fixture.ShowFixture;
import kr.codesquad.jazzmeet.fixture.VenueFixture;
import kr.codesquad.jazzmeet.image.entity.Image;
import kr.codesquad.jazzmeet.show.entity.Show;
import kr.codesquad.jazzmeet.show.vo.ShowSummaryWithVenue;
import kr.codesquad.jazzmeet.show.vo.ShowWithVenue;
import kr.codesquad.jazzmeet.venue.entity.Venue;
import kr.codesquad.jazzmeet.venue.repository.VenueRepository;

@Transactional
class ShowRepositoryTest extends IntegrationTestSupport {

	@Autowired
	ShowRepository showRepository;

	@Autowired
	ShowQueryRepository showQueryRepository;

	@Autowired
	VenueRepository venueRepository;

	@DisplayName("공연장의 id와 날짜가 주어지면 해당하는 공연 목록을 응답한다.")
	@Test
	void findShowsByVenueIdAndDate() throws Exception {
		//given
		LocalDate date = LocalDate.of(2023, 11, 3);
		Venue venue = VenueFixture.createVenue("부기우기", "경기도 고양시");
		Show show1 = ShowFixture.createShow("트리오", LocalDateTime.of(2023, 11, 3, 18, 00),
			LocalDateTime.of(2023, 11, 3, 20, 00), venue);
		Show show2 = ShowFixture.createShow("퀄텟", LocalDateTime.of(2023, 11, 3, 20, 00),
			LocalDateTime.of(2023, 11, 3, 22, 00), venue);
		showRepository.saveAll(List.of(show1, show2));

		Long venueId = venue.getId();

		//when
		List<Show> shows = showRepository.findByVenueIdAndDate(venueId, date);

		//then
		assertThat(shows).hasSize(2)
			.extracting("teamName")
			.contains("트리오", "퀄텟");
	}

	@DisplayName("date가 주어지면 그 달에 공연이 존재하는 날짜를 조회한다.")
	@Test
	void getShowCalendar() throws Exception {
		//given
		LocalDate date = LocalDate.of(2023, 11, 01);

		Venue venue1 = VenueFixture.createVenue("부기우기", "경기도 고양시");
		Venue venue2 = VenueFixture.createVenue("클럽에반스", "경기도 고양시");

		Show show1 = ShowFixture.createShow("트리오", LocalDateTime.of(2023, 11, 1, 18, 00),
			LocalDateTime.of(2023, 11, 1, 20, 00), venue1);
		Show show2 = ShowFixture.createShow("트리오", LocalDateTime.of(2023, 11, 10, 18, 00),
			LocalDateTime.of(2023, 11, 10, 20, 00), venue1);
		Show show3 = ShowFixture.createShow("트리오", LocalDateTime.of(2023, 11, 20, 18, 00),
			LocalDateTime.of(2023, 11, 20, 20, 00), venue2);
		showRepository.saveAll(List.of(show1, show2, show3));

		//when
		List<Integer> result = showQueryRepository.getShowCalendar(date);

		//then
		assertThat(result).hasSize(3)
			.isEqualTo(List.of(1, 10, 20));
	}

	@DisplayName("요청 날짜에 해당하는 공연 목록과 공연장을 공연장의 이름과 공연 시작시간으로 정렬하여 조회한다.")
	@Test
	void getShowsWithVenueByDate() throws Exception {
		//given
		LocalDate date = LocalDate.of(2023, 11, 1);
		Venue venue1 = VenueFixture.createVenue("부기우기", "경기 고양시 덕양구 마포로2");
		Venue venue2 = VenueFixture.createVenue("Entry55", "서울 마포구 마포로2");
		Venue venue3 = VenueFixture.createVenue("클럽에반스", "서울 마포구 마포로2");

		Show show1 = ShowFixture.createShow("부기우기 트리오1", LocalDateTime.of(2023, 11, 1, 20, 00), venue1);
		Show show2 = ShowFixture.createShow("부기우기 트리오2", LocalDateTime.of(2023, 11, 1, 18, 00), venue1);
		Show show3 = ShowFixture.createShow("Entry55 트리오1", LocalDateTime.of(2023, 11, 1, 20, 00), venue2);
		Show show4 = ShowFixture.createShow("Entry55 트리오2", LocalDateTime.of(2023, 11, 1, 18, 00), venue2);
		Show show5 = ShowFixture.createShow("클럽에반스 퀄텟", LocalDateTime.of(2023, 11, 1, 16, 00), venue3);

		showRepository.saveAll(List.of(show1, show2, show3, show4, show5));

		//when
		List<ShowWithVenue> shows = showQueryRepository.getShowsByDate(date);

		//then
		assertThat(shows).hasSize(3)
			.extracting("name", "cityAndDistrict")
			.containsExactly(
				tuple("Entry55", "서울 마포구"),
				tuple("부기우기", "경기 고양시 덕양구"),
				tuple("클럽에반스", "서울 마포구"));

		assertThat(shows.get(0).getShows()).hasSize(2)
			.extracting("teamName", "startTime")
			.containsExactly(
				tuple("Entry55 트리오2", LocalDateTime.of(2023, 11, 1, 18, 00)),
				tuple("Entry55 트리오1", LocalDateTime.of(2023, 11, 1, 20, 00))
			);

		assertThat(shows.get(1).getShows()).hasSize(2)
			.extracting("teamName", "startTime")
			.containsExactly(
				tuple("부기우기 트리오2", LocalDateTime.of(2023, 11, 1, 18, 00)),
				tuple("부기우기 트리오1", LocalDateTime.of(2023, 11, 1, 20, 00))
			);

		assertThat(shows.get(2).getShows()).hasSize(1)
			.extracting("teamName", "startTime")
			.containsExactly(
				tuple("클럽에반스 퀄텟", LocalDateTime.of(2023, 11, 1, 16, 00))
			);
	}

	@DisplayName("관리자는 검색어와 현재 페이지 숫자, 리스트 개수로 공연장 이름에 해당하는 공연 목록을 조회한다.")
	@Test
	void getShowsWhenContainVenue() throws Exception {
		//given
		PageRequest pageRequest = PageRequest.of(0, 10);
		String word = "부기우기";

		Venue venue1 = VenueFixture.createVenue("부기우기", "경기 고양시 덕양구");
		Venue venue2 = VenueFixture.createVenue("Entry55", "서울 마포구");

		List<Show> shows = new ArrayList<>();
		for (int i = 1; i <= 20; i++) {
			shows.add(ShowFixture.createShow("부 트리오" + i, LocalDateTime.of(2023, 11, 1, 20, 00), venue1));
		}
		shows.add(ShowFixture.createShow("Entry55 퀄텟1", LocalDateTime.of(2023, 11, 1, 20, 00), venue2));
		shows.add(ShowFixture.createShow("Entry55 퀄텟2", LocalDateTime.of(2023, 11, 1, 18, 00), venue2));

		showRepository.saveAll(shows);

		//when
		Page<ShowSummaryWithVenue> response = showQueryRepository.getShows(word, pageRequest);

		//then
		assertThat(response.getTotalPages()).isEqualTo(2);
		assertThat(response.getTotalElements()).isEqualTo(20);
		assertThat(response.getNumber()).isEqualTo(0);

		List<String> results = new ArrayList<>();
		for (int i = 1; i <= 10; i++) {
			results.add("부 트리오" + i);
		}

		assertThat(response.getContent()).hasSize(10)
			.extracting("teamName")
			.containsAll(results);
	}

	@DisplayName("관리자는 검색어와 현재 페이지 숫자, 리스트 개수로 공연 이름에 해당하는 공연 목록을 조회한다.")
	@Test
	void getShowsWhenContainTeam() throws Exception {
		//given
		PageRequest pageRequest = PageRequest.of(0, 10);
		String word = "퀄텟";

		Venue venue1 = VenueFixture.createVenue("부기우기", "경기 고양시 덕양구");
		Venue venue2 = VenueFixture.createVenue("Entry55", "서울 마포구");

		List<Show> shows = new ArrayList<>();
		for (int i = 1; i <= 20; i++) {
			shows.add(ShowFixture.createShow("부 트리오" + i, LocalDateTime.of(2023, 11, 1, 20, 00), venue1));
		}
		shows.add(ShowFixture.createShow("Entry55 퀄텟1", LocalDateTime.of(2023, 11, 1, 20, 00), venue2));
		shows.add(ShowFixture.createShow("Entry55 퀄텟2", LocalDateTime.of(2023, 11, 1, 18, 00), venue2));

		showRepository.saveAll(shows);

		//when
		Page<ShowSummaryWithVenue> response = showQueryRepository.getShows(word, pageRequest);

		//then
		assertThat(response.getTotalPages()).isEqualTo(1);
		assertThat(response.getTotalElements()).isEqualTo(2);
		assertThat(response.getNumber()).isEqualTo(0);

		assertThat(response.getContent()).hasSize(2)
			.extracting("teamName")
			.contains("Entry55 퀄텟1", "Entry55 퀄텟2");
	}

	@DisplayName("공연의 id로 공연을 조회한다.")
	@Test
	void getShowById() throws Exception {
		//given
		LocalDateTime time = LocalDateTime.of(2023, 11, 1, 20, 00);
		Image image = ImageFixture.createImage("url");
		Venue venue = VenueFixture.createVenue("부기우기", "경기 고양시 덕양구");
		Show show = ShowFixture.createShow("Entry55 퀄텟1", time, venue, image);
		showRepository.save(show);

		Long id = show.getId();

		//when
		Show findShow = showRepository.findEntireShowById(id).get();

		//then
		Assertions.assertAll(
			() -> assertThat(findShow)
				.extracting("teamName", "startTime", "endTime")
				.contains("Entry55 퀄텟1", time, time),

			() -> assertThat(findShow.getPoster())
				.extracting("id", "url")
				.contains(image.getId(), "url"),

			() -> assertThat(findShow.getVenue())
				.extracting("name")
				.isEqualTo("부기우기")
		);
	}
}
