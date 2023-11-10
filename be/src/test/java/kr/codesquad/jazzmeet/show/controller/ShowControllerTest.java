package kr.codesquad.jazzmeet.show.controller;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import kr.codesquad.jazzmeet.show.dto.ShowResponse;
import kr.codesquad.jazzmeet.show.dto.response.ExistShowCalendarResponse;
import kr.codesquad.jazzmeet.show.dto.response.ShowByDateResponse;
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

	@DisplayName("date에 해당하는 달의 공연이 있는 날을 조회한다.")
	@Test
	void getShowCalendar() throws Exception {
		//given
		String date = "20231107";
		when(showService.getShowCalendar(date)).thenReturn(new ExistShowCalendarResponse(List.of()));

		//when //then
		mockMvc.perform(
				get("/api/shows/calendar")
					.queryParam("date", date)
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.hasShow", instanceOf(List.class)));
	}

	@DisplayName("date에 해당하는 날의 공연 목록을 조회한다.")
	@Test
	void getShowsByDate() throws Exception {
		//given
		String date = "20231101";
		when(showService.getShowsByDate(date)).thenReturn(List.of(new ShowByDateResponse("서울시 강남구", List.of())));

		//when //then
		mockMvc.perform(
				get("/api/shows/by-region")
					.queryParam("date", date)
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", instanceOf(List.class)))
			.andExpect(jsonPath("$[0].region").value("서울시 강남구"))
			.andExpect(jsonPath("$[0].venues", instanceOf(List.class)));
	}

	@DisplayName("관리자가 공연장명과 page를 요청하면 해당하는 공연 목록을 조회한다.")
	@Test
	void getShows() throws Exception {
		//given
		String name = "부기우기";
		int currentPage = 1;
		when(showService.getShows(name, currentPage)).thenReturn(
			ShowResponse.builder().currentPage(1).maxPage(3).totalCount(30).shows(List.of()).build());

		//when //then
		mockMvc.perform(
				get("/api/shows")
					.queryParam("word", name)
					.queryParam("page", "1")
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.shows", instanceOf(List.class)))
			.andExpect(jsonPath("$.currentPage").value(1))
			.andExpect(jsonPath("$.maxPage").value(3))
			.andExpect(jsonPath("$.totalCount").value(30));
	}
}