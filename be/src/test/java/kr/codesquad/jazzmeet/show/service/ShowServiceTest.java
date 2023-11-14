package kr.codesquad.jazzmeet.show.service;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import kr.codesquad.jazzmeet.IntegrationTestSupport;
import kr.codesquad.jazzmeet.fixture.ImageFixture;
import kr.codesquad.jazzmeet.fixture.ShowFixture;
import kr.codesquad.jazzmeet.fixture.VenueFixture;
import kr.codesquad.jazzmeet.global.error.CustomException;
import kr.codesquad.jazzmeet.global.error.statuscode.ShowErrorCode;
import kr.codesquad.jazzmeet.image.entity.Image;
import kr.codesquad.jazzmeet.image.repository.ImageRepository;
import kr.codesquad.jazzmeet.show.dto.request.RegisterShowRequest;
import kr.codesquad.jazzmeet.show.dto.response.ExistShowCalendarResponse;
import kr.codesquad.jazzmeet.show.dto.response.RegisterShowResponse;
import kr.codesquad.jazzmeet.show.dto.response.ShowByDateAndVenueResponse;
import kr.codesquad.jazzmeet.show.dto.response.ShowByDateResponse;
import kr.codesquad.jazzmeet.show.dto.response.ShowDetailResponse;
import kr.codesquad.jazzmeet.show.dto.response.ShowResponse;
import kr.codesquad.jazzmeet.show.dto.response.UpcomingShowResponse;
import kr.codesquad.jazzmeet.show.entity.Show;
import kr.codesquad.jazzmeet.show.repository.ShowRepository;
import kr.codesquad.jazzmeet.venue.entity.Venue;
import kr.codesquad.jazzmeet.venue.repository.VenueRepository;

class ShowServiceTest extends IntegrationTestSupport {

	@Autowired
	ShowService showService;
	@Autowired
	ShowRepository showRepository;
	@Autowired
	VenueRepository venueRepository;
	@Autowired
	ImageRepository imageRepository;

	@AfterEach
	void dbClean() {
		showRepository.deleteAllInBatch();
		venueRepository.deleteAllInBatch();
		imageRepository.deleteAllInBatch();
	}

	@Test
	@DisplayName("메인 페이지에 방문하면 진행 중인 공연 목록을 조회한다.")
	void getUpcomingShows() {
		// given
		// 공연 목록 생성
		LocalDateTime nowTime = LocalDateTime.of(2023, Month.OCTOBER, 29, 17, 0);
		LocalDateTime show1startTime = LocalDateTime.of(2023, Month.OCTOBER, 28, 18, 0);
		LocalDateTime show1endTime = LocalDateTime.of(2023, Month.OCTOBER, 28, 19, 0);
		Show show1 = ShowFixture.createShow("팀 이름1", show1startTime, show1endTime);// 지난 공연
		LocalDateTime show2startTime = LocalDateTime.of(2023, Month.OCTOBER, 29, 18, 0);
		LocalDateTime show2endTime = LocalDateTime.of(2023, Month.OCTOBER, 29, 19, 0);
		Show show2 = ShowFixture.createShow("팀 이름2", show2startTime, show2endTime);// 진행 중인 공연
		LocalDateTime show3startTime = LocalDateTime.of(2023, Month.OCTOBER, 30, 20, 0);
		LocalDateTime show3endTime = LocalDateTime.of(2023, Month.OCTOBER, 30, 21, 0);
		Show show3 = ShowFixture.createShow("팀 이름3", show3startTime, show3endTime);// 진행 예정 공연

		// 공연 3개 저장
		showRepository.save(show1);
		showRepository.save(show2);
		showRepository.save(show3);

		// when
		// 공연 목록을 조회 했을 때
		List<UpcomingShowResponse> shows = showService.getUpcomingShows(nowTime);

		// then
		// 1. show1이 없는지 (지난 공연은 포함하지 않는지) 확인
		// 2. show2, show3가 존재하는지 확인
		assertThat(shows).extracting(UpcomingShowResponse::showName)
			.doesNotContain(show1.getTeamName())
			.contains(show2.getTeamName())
			.contains(show3.getTeamName());
	}

	@Test
	@DisplayName("메인 페이지에 방문하면 진행 중인 공연 목록을 공연 시작 시간이 빠른 순서대로 조회한다.")
	void getSortedUpcomingShows() {
		// given
		// 공연 목록 생성
		LocalDateTime nowTime = LocalDateTime.of(2023, Month.OCTOBER, 29, 17, 0);
		LocalDateTime show1startTime = LocalDateTime.of(2023, Month.OCTOBER, 30, 20, 0);
		LocalDateTime show1endTime = LocalDateTime.of(2023, Month.OCTOBER, 30, 21, 0);
		Show show1 = ShowFixture.createShow("팀 이름3", show1startTime, show1endTime);// 진행 예정 공연2
		LocalDateTime show2startTime = LocalDateTime.of(2023, Month.OCTOBER, 29, 20, 0);
		LocalDateTime show2endTime = LocalDateTime.of(2023, Month.OCTOBER, 29, 21, 0);
		Show show2 = ShowFixture.createShow("팀 이름3", show2startTime, show2endTime);// 진행 예정 공연1

		// 공연 2개 저장
		showRepository.save(show1);
		showRepository.save(show2);

		// when
		List<UpcomingShowResponse> shows = showService.getUpcomingShows(nowTime);

		// then
		// show2 -> show1 순서대로 정렬되어 출력되는지 확인
		assertThat(shows.get(0).showName()).isEqualTo(show2.getTeamName());
		assertThat(shows.get(1).showName()).isEqualTo(show1.getTeamName());
	}

	@Test
	@DisplayName("메인 페이지에 방문했을 때 진행 중인 공연이 없다면 빈 목록을 조회한다.")
	void getEmptyUpcomingShows() {
		// given
		// 공연 목록 생성
		LocalDateTime nowTime = LocalDateTime.of(2023, Month.OCTOBER, 29, 17, 0);
		LocalDateTime show1startTime = LocalDateTime.of(2023, Month.OCTOBER, 27, 20, 0);
		LocalDateTime show1endTime = LocalDateTime.of(2023, Month.OCTOBER, 27, 21, 0);
		Show show1 = ShowFixture.createShow("팀 이름3", show1startTime, show1endTime);// 진행 예정 공연2
		LocalDateTime show2startTime = LocalDateTime.of(2023, Month.OCTOBER, 28, 20, 0);
		LocalDateTime show2endTime = LocalDateTime.of(2023, Month.OCTOBER, 28, 21, 0);
		Show show2 = ShowFixture.createShow("팀 이름3", show2startTime, show2endTime);// 진행 예정 공연1

		// 공연 2개 저장
		showRepository.save(show1);
		showRepository.save(show2);

		// when
		List<UpcomingShowResponse> shows = showService.getUpcomingShows(nowTime);

		// then
		// 배열이 비어있는지 확인
		assertThat(shows).isEmpty();
	}

	@DisplayName("요청에 날짜가 들어가지 않는다면 빈 배열을 응답한다.")
	@Test
	void findEmptyShowsWhenNotDate() throws Exception {
		//given
		String date = null;
		Long venueId = 1L;
		Venue venue = VenueFixture.createVenue("부기우기", "경기도 고양시");
		Show show = ShowFixture.createShow("부기우기 트리오1", LocalDateTime.of(2023, 11, 1, 18, 00), venue);
		showRepository.save(show);

		//when
		List<ShowByDateAndVenueResponse> shows = showService.getShowsByDate(venueId, date);

		//then
		assertThat(shows).hasSize(0);
	}

	@DisplayName("날짜와 공연장 Id가 주어지면 해당하는 공연 목록을 응답한다.")
	@Test
	void findShows() throws Exception {
		//given
		String date = "20231101";

		Venue venue = VenueFixture.createVenue("부기우기", "경기도 고양시");
		Show show1 = ShowFixture.createShow("부기우기 트리오1", LocalDateTime.of(2023, 11, 1, 18, 00), venue);
		Show show2 = ShowFixture.createShow("부기우기 트리오2", LocalDateTime.of(2023, 11, 1, 20, 00), venue);
		showRepository.saveAll(List.of(show1, show2));

		Long venueId = venue.getId();

		//when
		List<ShowByDateAndVenueResponse> shows = showService.getShowsByDate(venueId, date);

		//then
		assertThat(shows).hasSize(2)
			.extracting("teamName")
			.contains("부기우기 트리오1", "부기우기 트리오2");
	}

	@DisplayName("날짜 형식이 올바르지 않으면 예외가 발생한다.")
	@Test
	void findShowsException() throws Exception {
		//given
		String date = "2023-11-03";
		Long venueId = 1L;

		Venue venue = VenueFixture.createVenue("부기우기", "경기도 고양시");
		Show show1 = ShowFixture.createShow("부기우기 트리오1", LocalDateTime.of(2023, 11, 1, 18, 00), venue);
		Show show2 = ShowFixture.createShow("부기우기 트리오2", LocalDateTime.of(2023, 11, 1, 20, 00), venue);
		showRepository.saveAll(List.of(show1, show2));

		//when//then
		assertThatThrownBy(() -> showService.getShowsByDate(venueId, date))
			.isInstanceOf(CustomException.class);
	}

	@DisplayName("date가 주어지면 해당하는 달의 공연이 있는 날짜를 조회한다.")
	@Test
	void getShowCalendar() throws Exception {
		//given
		String date = "202311";

		Venue venue1 = VenueFixture.createVenue("부기우기", "경기도 고양시");
		Venue venue2 = VenueFixture.createVenue("클럽에반스", "경기도 고양시");

		Show show1 = ShowFixture.createShow("Entry55 트리오1", LocalDateTime.of(2023, 11, 3, 18, 00), venue2);
		Show show2 = ShowFixture.createShow("부기우기 트리오2", LocalDateTime.of(2023, 11, 2, 20, 00), venue1);
		Show show3 = ShowFixture.createShow("부기우기 트리오1", LocalDateTime.of(2023, 11, 1, 18, 00), venue1);

		showRepository.saveAll(List.of(show1, show2, show3));

		//when
		ExistShowCalendarResponse showCalendar = showService.getShowCalendar(date);

		//then
		assertThat(showCalendar)
			.extracting("hasShow")
			.isEqualTo(List.of(1, 2, 3));
	}

	@DisplayName("date의 형식이 다르면 예외가 발생한다.")
	@Test
	void getShowCalendarException() throws Exception {
		//given
		String date = "2023-11";

		Venue venue1 = VenueFixture.createVenue("부기우기", "경기도 고양시");
		Venue venue2 = VenueFixture.createVenue("클럽에반스", "경기도 고양시");

		Show show1 = ShowFixture.createShow("부기우기 트리오1", LocalDateTime.of(2023, 11, 1, 18, 00), venue1);
		Show show2 = ShowFixture.createShow("부기우기 트리오2", LocalDateTime.of(2023, 11, 1, 20, 00), venue1);
		Show show3 = ShowFixture.createShow("Entry55 트리오1", LocalDateTime.of(2023, 11, 1, 18, 00), venue2);
		showRepository.saveAll(List.of(show1, show2, show3));

		//when //then
		assertThatThrownBy(() -> showService.getShowCalendar(date))
			.isInstanceOf(CustomException.class);
	}

	@DisplayName("요청 날짜에 해당하는 공연 목록과 공연장을 시/구 주소로 감싸서 조회한다.")
	@Test
	void getShowsByDate() throws Exception {
		//given
		String date = "20231101";

		Venue venue1 = VenueFixture.createVenue("부기우기", "경기 고양시 덕양구");
		Venue venue2 = VenueFixture.createVenue("Entry55", "서울 마포구");
		Venue venue3 = VenueFixture.createVenue("클럽에반스", "서울 마포구");

		Show show1 = ShowFixture.createShow("부기우기 트리오1", LocalDateTime.of(2023, 11, 1, 20, 00), venue1);
		Show show2 = ShowFixture.createShow("부기우기 트리오2", LocalDateTime.of(2023, 11, 1, 18, 00), venue1);
		Show show3 = ShowFixture.createShow("Entry55 트리오1", LocalDateTime.of(2023, 11, 1, 20, 00), venue2);
		Show show4 = ShowFixture.createShow("Entry55 트리오2", LocalDateTime.of(2023, 11, 1, 18, 00), venue2);
		Show show5 = ShowFixture.createShow("클럽에반스 퀄텟", LocalDateTime.of(2023, 11, 1, 16, 00), venue3);

		showRepository.saveAll(List.of(show1, show2, show3, show4, show5));

		//when
		List<ShowByDateResponse> shows = showService.getShowsByDate(date);

		//then
		assertThat(shows).hasSize(2)
			.extracting("region")
			.containsExactly("경기 고양시 덕양구", "서울 마포구");

		assertThat(shows.get(0).venues()).hasSize(1)
			.extracting("name")
			.contains("부기우기");

		assertThat(shows.get(1).venues()).hasSize(2)
			.extracting("name")
			.containsExactly("Entry55", "클럽에반스");

		assertThat(shows.get(0).venues().get(0).getShows()).hasSize(2)
			.extracting("teamName", "startTime")
			.containsExactly(
				tuple("부기우기 트리오2", LocalDateTime.of(2023, 11, 1, 18, 00)),
				tuple("부기우기 트리오1", LocalDateTime.of(2023, 11, 1, 20, 00))
			);
	}

	@DisplayName("관리자가 검색어와 현재 페이지 숫자로 검색어에 해당하는 공연장의 공연 목록을 조회한다.")
	@Test
	void getShows() throws Exception {
		//given
		String word = "부기우기";
		int page = 2;

		Venue venue1 = VenueFixture.createVenue("부기우기", "경기 고양시 덕양구");
		Venue venue2 = VenueFixture.createVenue("Entry55", "서울 마포구");

		List<Show> shows = new ArrayList<>();
		for (int i = 1; i <= 20; i++) {
			shows.add(ShowFixture.createShow("부 트리오" + i, LocalDateTime.of(2023, 11, 1, 20, 00), venue1));
		}
		shows.add(ShowFixture.createShow("E 트리오1", LocalDateTime.of(2023, 11, 1, 20, 00), venue2));
		shows.add(ShowFixture.createShow("E 트리오2", LocalDateTime.of(2023, 11, 1, 18, 00), venue2));

		showRepository.saveAll(shows);

		//when
		ShowResponse showResponse = showService.getShows(word, page);

		//then
		List<String> results = new ArrayList<>();
		for (int i = 11; i <= 20; i++) {
			results.add("부 트리오" + i);
		}

		assertThat(showResponse)
			.extracting("totalCount", "currentPage", "maxPage")
			.containsExactly(20L, 2, 2);

		assertThat(showResponse.shows()).hasSize(10)
			.extracting("teamName")
			.containsAll(results);
	}

	@DisplayName("관리자가 검색어와 현재 페이지 숫자로 검색어에 해당하는 공연 이름을 가진 공연 목록을 조회한다.")
	@Test
	void getShowsByTeamName() throws Exception {
		//given
		String word = "퀄텟";
		int page = 1;

		Venue venue1 = VenueFixture.createVenue("부기우기", "경기 고양시 덕양구");
		Venue venue2 = VenueFixture.createVenue("Entry55", "서울 마포구");

		List<Show> shows = new ArrayList<>();
		for (int i = 1; i <= 20; i++) {
			shows.add(ShowFixture.createShow("부기우기 트리오" + i, LocalDateTime.of(2023, 11, 1, 20, 00), venue1));
		}
		shows.add(ShowFixture.createShow("Entry55 퀄텟1", LocalDateTime.of(2023, 11, 1, 20, 00), venue2));
		shows.add(ShowFixture.createShow("Entry55 퀄텟2", LocalDateTime.of(2023, 11, 1, 18, 00), venue2));

		showRepository.saveAll(shows);

		//when
		ShowResponse showResponse = showService.getShows(word, page);

		//then
		assertThat(showResponse)
			.extracting("totalCount", "currentPage", "maxPage")
			.containsExactly(2L, 1, 1);

		assertThat(showResponse.shows()).hasSize(2)
			.extracting("teamName")
			.contains("Entry55 퀄텟1", "Entry55 퀄텟2");
	}

	@DisplayName("관리자가 검색어를 입력하지 않으면 전체 공연 목록을 조회한다.")
	@Test
	void getShowsWhenWordNotExist() throws Exception {
		//given
		String word = null;
		int page = 1;

		Venue venue1 = VenueFixture.createVenue("부기우기", "경기 고양시 덕양구");
		Venue venue2 = VenueFixture.createVenue("Entry55", "서울 마포구");

		List<Show> shows = new ArrayList<>();
		for (int i = 1; i <= 20; i++) {
			shows.add(ShowFixture.createShow("부기우기 트리오" + i, LocalDateTime.of(2023, 11, 1, 20, 00), venue1));
		}
		shows.add(ShowFixture.createShow("Entry55 퀄텟1", LocalDateTime.of(2023, 11, 1, 20, 00), venue2));
		shows.add(ShowFixture.createShow("Entry55 퀄텟2", LocalDateTime.of(2023, 11, 1, 18, 00), venue2));

		showRepository.saveAll(shows);

		//when
		ShowResponse showResponse = showService.getShows(word, page);

		//then
		assertThat(showResponse)
			.extracting("totalCount", "currentPage", "maxPage")
			.containsExactly(22L, 1, 3);
	}

	@DisplayName("관리자가 id로 공연을 조회한다.")
	@Test
	void getShowById() throws Exception {
		//given
		Image image = ImageFixture.createImage("url");
		Venue venue = VenueFixture.createVenue("부기우기", "경기 고양시 덕양구");
		Show show = ShowFixture.createShow("Entry55 퀄텟1", LocalDateTime.of(2023, 11, 1, 20, 00), venue, image);
		showRepository.save(show);

		Long id = show.getId();

		//when
		ShowDetailResponse showDetail = showService.getShowDetail(id);

		//then
		Assertions.assertAll(
			() -> assertThat(showDetail)
				.extracting("id", "teamName", "venueName", "startTime", "endTime")
				.contains(show.getId(), "Entry55 퀄텟1", "부기우기", LocalDateTime.of(2023, 11, 1, 20, 00),
					LocalDateTime.of(2023, 11, 1, 20, 00)),

			() -> assertThat(showDetail.poster())
				.extracting("id", "url")
				.contains(image.getId(), "url")
		);
	}

	@DisplayName("존재하지 않는 공연을 조회하려고 하는 경우 예외가 발생한다.")
	@Test
	void getShowByNotExistId() throws Exception {
		//given
		Image image = ImageFixture.createImage("url");
		Venue venue = VenueFixture.createVenue("부기우기", "경기 고양시 덕양구");
		Show show = ShowFixture.createShow("Entry55 퀄텟1", LocalDateTime.of(2023, 11, 1, 20, 00), venue, image);
		showRepository.save(show);

		Long id = 0L;

		//when //then
		assertThatThrownBy(() -> showService.getShowDetail(id))
			.isInstanceOf(CustomException.class)
			.hasMessage(ShowErrorCode.NOT_FOUND_SHOW.getMessage());
	}

	@DisplayName("관리자가 공연을 등록한다.")
	@Test
	void saveShow() throws Exception {
		//given
		Venue venue = VenueFixture.createVenue("부기우기", "경기 고양시 덕양구");
		venueRepository.save(venue);

		Image poster = ImageFixture.createImage("url");
		imageRepository.save(poster);

		Long venueId = venue.getId();
		RegisterShowRequest request = RegisterShowRequest.builder()
			.teamName("Entry55 퀄텟")
			.posterId(poster.getId())
			.description("description")
			.startTime(LocalDateTime.of(2023, 11, 13, 11, 50))
			.endTime(LocalDateTime.of(2023, 11, 13, 11, 50))
			.build();

		//when
		RegisterShowResponse response = showService.registerShow(venueId, request);

		//then
		assertThat(response.id()).isNotNull();
	}

	@DisplayName("관리자가 show id로 공연을 수정한다.")
	@Test
	void updateShow() throws Exception {
		//given
		Venue venue = VenueFixture.createVenue("부기우기", "서울 마포구");
		Show show = ShowFixture.createShow("퀄텟", LocalDateTime.of(2023, 11, 14, 15, 0), venue);
		showRepository.save(show);

		Image poster = ImageFixture.createImage("url");
		imageRepository.save(poster);

		Long showId = show.getId();
		LocalDateTime startTime = LocalDateTime.of(2023, 11, 14, 18, 0);
		RegisterShowRequest request = RegisterShowRequest.builder()
			.teamName("수정된 팀 명")
			.description("수정됨")
			.posterId(poster.getId())
			.startTime(startTime)
			.endTime(startTime.plusHours(2))
			.build();

		//when
		ShowDetailResponse response = showService.updateShow(showId, request);

		//then
		Assertions.assertAll(
			() -> assertThat(response)
				.extracting("teamName", "venueName", "description", "startTime", "endTime")
				.contains("수정된 팀 명", "부기우기", "수정됨", startTime, startTime.plusHours(2)),
			() -> assertThat(response.poster())
				.extracting("url")
				.isEqualTo("url"));
	}

	@DisplayName("관리자가 공연 id로 공연을 삭제한다.")
	@Test
	void deleteShow() throws Exception {
		//given
		Venue venue = VenueFixture.createVenue("부기우기", "서울 마포구");
		Show show = ShowFixture.createShow("퀄텟", LocalDateTime.of(2023, 11, 14, 15, 0), venue);
		showRepository.save(show);

		Long showId = show.getId();

		//when
		showService.deleteShow(showId);

		assertThat(showRepository.findById(showId)).isEmpty();
	}
}