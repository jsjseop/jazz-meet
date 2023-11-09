package kr.codesquad.jazzmeet.show.repository;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import kr.codesquad.jazzmeet.IntegrationTestSupport;
import kr.codesquad.jazzmeet.fixture.ShowFixture;
import kr.codesquad.jazzmeet.fixture.VenueFixture;
import kr.codesquad.jazzmeet.show.entity.Show;
import kr.codesquad.jazzmeet.venue.entity.Venue;

@Transactional
class ShowRepositoryTest extends IntegrationTestSupport {

	@Autowired
	ShowRepository showRepository;

	@Autowired
	ShowQueryRepository showQueryRepository;

	@DisplayName("공연장의 id와 날짜가 주어지면 해당하는 공연 목록을 응답한다.")
	@Test
	void findShowsByVenueIdAndDate() throws Exception {
		//given
		LocalDate date = LocalDate.of(2023, 11, 3);
		Long venueId = 1L;

		Venue venue = VenueFixture.createVenue("부기우기", "경기도 고양시");
		Show show1 = ShowFixture.createShow("트리오", LocalDateTime.of(2023, 11, 3, 18, 00),
			LocalDateTime.of(2023, 11, 3, 20, 00), venue);
		Show show2 = ShowFixture.createShow("퀄텟", LocalDateTime.of(2023, 11, 3, 20, 00),
			LocalDateTime.of(2023, 11, 3, 22, 00), venue);
		showRepository.saveAll(List.of(show1, show2));

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
}
