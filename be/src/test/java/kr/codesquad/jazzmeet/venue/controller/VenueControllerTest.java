package kr.codesquad.jazzmeet.venue.controller;

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

import kr.codesquad.jazzmeet.venue.service.VenueService;

@WebMvcTest(controllers = VenueController.class)
class VenueControllerTest {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	VenueService venueService;

	@DisplayName("검색어에 해당하는 공연장의 위치 정보 목록을 조회한다.")
	@Test
	public void findVenuesPinsByWord() throws Exception {
		//given
		String word = "부기우기";

		//when //then
		mockMvc.perform(
				get("/api/venues/pins/search")
					.queryParam("word", word)
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", instanceOf(List.class)));
	}

	@DisplayName("요청으로 들어온 위치의 범위 안에 해당하는 공연장의 위치 정보 목록을 조회한다.")
	@Test
	public void findVenuePinsByLocation() throws Exception {
	    //given
		Double lowLatitude = 37.51387497068088 ;
		Double highLatitude = 37.61077342780979 ;
		Double lowLongitude = 126.9293615244093 ;
		Double highLongitude = 127.10246683663273 ;


	    //when //then
		mockMvc.perform(
				get("/api/venues/pins/map")
					.queryParam("lowLatitude", String.valueOf(lowLatitude))
					.queryParam("highLatitude", String.valueOf(highLatitude))
					.queryParam("lowLongitude", String.valueOf(lowLongitude))
					.queryParam("highLongitude", String.valueOf(highLongitude))
					.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", instanceOf(List.class)));
	}
}
