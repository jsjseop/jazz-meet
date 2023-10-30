package kr.codesquad.jazzmeet.venue.controller;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = VenueController.class)
class VenueControllerTest {

	@Autowired
	private MockMvc mockMvc;

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
}