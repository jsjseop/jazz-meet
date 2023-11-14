package kr.codesquad.jazzmeet.show.controller;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.codesquad.jazzmeet.show.dto.request.RegisterShowRequest;
import kr.codesquad.jazzmeet.show.dto.response.ExistShowCalendarResponse;
import kr.codesquad.jazzmeet.show.dto.response.RegisterShowResponse;
import kr.codesquad.jazzmeet.show.dto.response.ShowByDateResponse;
import kr.codesquad.jazzmeet.show.dto.response.ShowDetailResponse;
import kr.codesquad.jazzmeet.show.dto.response.ShowResponse;
import kr.codesquad.jazzmeet.show.service.ShowService;
import kr.codesquad.jazzmeet.show.vo.ShowPoster;

@WebMvcTest(controllers = ShowController.class)
class ShowControllerTest {

	@Autowired
	ObjectMapper objectMapper;

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

	@DisplayName("관리자가 공연의 id로 공연을 상세 조회한다.")
	@Test
	void getShowDetail() throws Exception {
		//given
		Long showId = 1L;
		when(showService.getShowDetail(any())).thenReturn(
			ShowDetailResponse.builder().id(showId).poster(new ShowPoster(1L, "url")).teamName("퀄텟").build());

		//when //then
		mockMvc.perform(
				get("/api/shows/{showId}", showId)
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id").value(1L))
			.andExpect(jsonPath("$.poster.id").value(1L))
			.andExpect(jsonPath("$.poster.url").value("url"))
			.andExpect(jsonPath("$.teamName").value("퀄텟"));
	}

	@DisplayName("관리자가 공연장 페이지에서 공연 등록을 한다.")
	@Test
	void registerShow() throws Exception {
		//given
		Long venueId = 1L;
		RegisterShowRequest request = RegisterShowRequest.builder()
			.teamName("러스틱재즈 트리오")
			.description("러스틱 재즈 설명")
			.posterId(1L)
			.startTime(LocalDateTime.of(2023, 11, 13, 17, 00))
			.endTime(LocalDateTime.of(2023, 11, 13, 19, 00))
			.build();

		when(showService.registerShow(any(), any())).thenReturn(new RegisterShowResponse(1L));

		//when //then
		mockMvc.perform(
				post("/api/shows/{venueId}", venueId)
					.content(objectMapper.writeValueAsString(request))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.id").value(1L));
	}

	@DisplayName("관리자가 공연장 페이지에서 공연 등록을 할 때 팀 이름은 반드시 필요하다.")
	@Test
	void registerShowWhenNotExistName() throws Exception {
		//given
		Long venueId = 1L;
		RegisterShowRequest request = RegisterShowRequest.builder()
			.description("러스틱 재즈 설명")
			.posterId(1L)
			.startTime(LocalDateTime.of(2023, 11, 13, 17, 00))
			.endTime(LocalDateTime.of(2023, 11, 13, 19, 00))
			.build();

		when(showService.registerShow(any(), any())).thenReturn(new RegisterShowResponse(1L));

		//when //then
		mockMvc.perform(
				post("/api/shows/{venueId}", venueId)
					.content(objectMapper.writeValueAsString(request))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isBadRequest());
	}

	@DisplayName("관리자가 공연장 페이지에서 공연 등록을 할 때 팀 이름은 50자까지 가능하다.")
	@Test
	void registerShowWhenOutOfFifty() throws Exception {
		//given
		Long venueId = 1L;

		RegisterShowRequest request = RegisterShowRequest.builder()
			.teamName("아".repeat(51))
			.description("러스틱 재즈 설명")
			.posterId(1L)
			.startTime(LocalDateTime.of(2023, 11, 13, 17, 00))
			.endTime(LocalDateTime.of(2023, 11, 13, 19, 00))
			.build();

		//when //then
		mockMvc.perform(
				post("/api/shows/{venueId}", venueId)
					.content(objectMapper.writeValueAsString(request))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isBadRequest());
	}

	@DisplayName("관리자가 공연장 페이지에서 공연 등록을 할 때 설명은 1000자까지 가능하다.")
	@Test
	void registerShowWhenOutOfThousand() throws Exception {
		//given
		Long venueId = 1L;

		RegisterShowRequest request = RegisterShowRequest.builder()
			.teamName("러스틱 재즈")
			.description("러".repeat(1001))
			.posterId(1L)
			.startTime(LocalDateTime.of(2023, 11, 13, 17, 00))
			.endTime(LocalDateTime.of(2023, 11, 13, 19, 00))
			.build();

		//when //then
		mockMvc.perform(
				post("/api/shows/{venueId}", venueId)
					.content(objectMapper.writeValueAsString(request))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isBadRequest());
	}

	@DisplayName("관리자가 공연장 페이지에서 공연 등록을 할 때 poster id는 반드시 필요하다.")
	@Test
	void registerShowNotExistPoster() throws Exception {
		//given
		Long venueId = 1L;

		RegisterShowRequest request = RegisterShowRequest.builder()
			.teamName("러스틱 재즈")
			.description("러스틱 재즈 설명")
			.startTime(LocalDateTime.of(2023, 11, 13, 17, 00))
			.endTime(LocalDateTime.of(2023, 11, 13, 19, 00))
			.build();

		//when //then
		mockMvc.perform(
				post("/api/shows/{venueId}", venueId)
					.content(objectMapper.writeValueAsString(request))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isBadRequest());
	}

	@DisplayName("관리자가 공연을 수정한다.")
	@Test
	void updateShow() throws Exception {
		//given
		Long showId = 1L;
		RegisterShowRequest request = RegisterShowRequest.builder()
			.teamName("부기우기 트리오")
			.description("설명")
			.posterId(1L)
			.startTime(LocalDateTime.of(2023, 11, 14, 13, 0))
			.endTime(LocalDateTime.of(2023, 11, 14, 14, 0))
			.build();

		ShowDetailResponse response = ShowDetailResponse.builder()
			.id(1L)
			.teamName("부기우기 트리오")
			.description("설명")
			.venueName("부기우기")
			.poster(new ShowPoster(1L, "url"))
			.startTime(LocalDateTime.of(2023, 11, 14, 13, 0))
			.endTime(LocalDateTime.of(2023, 11, 14, 14, 0))
			.build();

		when(showService.updateShow(any(), any())).thenReturn(response);

		//when //then
		mockMvc.perform(
				put("/api/shows/{showId}", showId)
					.content(objectMapper.writeValueAsString(request))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(jsonPath("$.id").value(response.id()))
			.andExpect(jsonPath("$.teamName").value(response.teamName()))
			.andExpect(jsonPath("$.venueName").value(response.venueName()))
			.andExpect(jsonPath("$.description").value(response.description()))
			.andExpect(jsonPath("$.poster.id").value(response.poster().getId()))
			.andExpect(jsonPath("$.poster.url").value(response.poster().getUrl()));
	}

	@DisplayName("관리자는 공연 Id로 공연을 삭제한다.")
	@Test
	void deleteShow() throws Exception {
		//given
		Long showId = 1L;

		//when //then
		mockMvc.perform(
				delete("/api/shows/{showId}", showId)
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isNoContent());
	}
}