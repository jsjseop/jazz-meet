package kr.codesquad.jazzmeet.show.controller;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import kr.codesquad.jazzmeet.show.service.ShowService;

@WebMvcTest(controllers = ShowController.class)
class ShowControllerTest {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	ShowService showService;

	@DisplayName("venue id와 date로 해당 공연의 해당 날짜의 공연 리스트가 조회된다.")
	@Test
	void findShowsByVenueIdAndDate() throws Exception {
		//given
		Long venueId = 1L;
		String date = "20231103";

		//when //then
		mockMvc.perform(
				get("/api/venues/{venueId}/shows", venueId)
					.queryParam("date", date)
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", instanceOf(List.class)));
	}
}