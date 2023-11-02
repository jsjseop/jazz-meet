package kr.codesquad.jazzmeet.show.service;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import kr.codesquad.jazzmeet.IntegrationTestSupport;
import kr.codesquad.jazzmeet.fixture.ShowFixture;
import kr.codesquad.jazzmeet.show.dto.response.UpcomingShowResponse;
import kr.codesquad.jazzmeet.show.entity.Show;
import kr.codesquad.jazzmeet.show.repository.ShowRepository;

class ShowServiceTest extends IntegrationTestSupport {

	@Autowired
	ShowService showService;
	@Autowired
	ShowRepository showRepository;

	@AfterEach
	void dbClean() {
		showRepository.deleteAllInBatch();
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
}