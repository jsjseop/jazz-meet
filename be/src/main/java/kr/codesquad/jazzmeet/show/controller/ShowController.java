package kr.codesquad.jazzmeet.show.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.codesquad.jazzmeet.show.dto.response.UpcomingShowResponse;
import kr.codesquad.jazzmeet.show.service.ShowService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class ShowController {

	private final ShowService showService;

	@GetMapping("/api/shows/upcoming")
	public ResponseEntity<List<UpcomingShowResponse>> getUpcomingShows() {
		List<UpcomingShowResponse> upcomingShowResponses = showService.getUpcomingShows();
		return ResponseEntity.ok(upcomingShowResponses);
	}

}
