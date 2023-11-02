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
}