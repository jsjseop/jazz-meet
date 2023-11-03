package kr.codesquad.jazzmeet.show.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.codesquad.jazzmeet.show.dto.response.ExistShowCalendarResponse;
import kr.codesquad.jazzmeet.show.dto.response.ShowByDateResponse;
import kr.codesquad.jazzmeet.show.dto.response.UpcomingShowResponse;
import kr.codesquad.jazzmeet.show.service.ShowService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class ShowController {

	private final ShowService showService;

	@GetMapping("/api/shows/upcoming")
	public ResponseEntity<List<UpcomingShowResponse>> getUpcomingShows() {
		LocalDateTime nowTime = LocalDateTime.now();
		List<UpcomingShowResponse> upcomingShowResponses = showService.getUpcomingShows(nowTime);

		return ResponseEntity.ok(upcomingShowResponses);
	}

	/**
	 * 공연장의 날짜별 공연 목록 조회 API
	 */
	@GetMapping("/api/venues/{venueId}/shows")
	public ResponseEntity<List<ShowByDateResponse>> getShows(@PathVariable Long venueId,
		@RequestParam(required = false) String date) {
		List<ShowByDateResponse> shows = showService.getShows(venueId, date);

		return ResponseEntity.ok(shows);
	}

	/**
	 * 공연장 상세, 월간 공연 일정 유무 조회 API
	 */
	@GetMapping("/api/venues/{venueId}/shows/calendar")
	public ResponseEntity<?> getExistShowCalendar(@PathVariable Long venueId, @RequestParam String date) {
		ExistShowCalendarResponse existShows = showService.getExistShows(venueId, date);

		return ResponseEntity.ok(existShows);
	}
}
