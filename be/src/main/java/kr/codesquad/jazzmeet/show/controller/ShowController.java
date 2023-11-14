package kr.codesquad.jazzmeet.show.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import kr.codesquad.jazzmeet.show.dto.request.RegisterShowRequest;
import kr.codesquad.jazzmeet.show.dto.response.ExistShowCalendarResponse;
import kr.codesquad.jazzmeet.show.dto.response.RegisterShowResponse;
import kr.codesquad.jazzmeet.show.dto.response.ShowByDateAndVenueResponse;
import kr.codesquad.jazzmeet.show.dto.response.ShowByDateResponse;
import kr.codesquad.jazzmeet.show.dto.response.ShowDetailResponse;
import kr.codesquad.jazzmeet.show.dto.response.ShowResponse;
import kr.codesquad.jazzmeet.show.dto.response.UpcomingShowResponse;
import kr.codesquad.jazzmeet.show.service.ShowService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class ShowController {

	private final ShowService showService;

	/**
	 * 진행 중인 공연 조회 API
	 */
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
	public ResponseEntity<List<ShowByDateAndVenueResponse>> getShowsByDate(@PathVariable Long venueId,
		@RequestParam(required = false) String date) {
		List<ShowByDateAndVenueResponse> shows = showService.getShowsByDate(venueId, date);

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

	/**
	 * 월간 공연 일정 유무 조회 API
	 */
	@GetMapping("/api/shows/calendar")
	public ResponseEntity<ExistShowCalendarResponse> getShowCalendar(@RequestParam String date) {
		ExistShowCalendarResponse showCalendar = showService.getShowCalendar(date);

		return ResponseEntity.ok(showCalendar);
	}

	/**
	 * 날짜 별 공연 목록 조회 API
	 */
	@GetMapping("/api/shows/by-region")
	public ResponseEntity<List<ShowByDateResponse>> getShowsByDate(@RequestParam String date) {
		List<ShowByDateResponse> shows = showService.getShowsByDate(date);

		return ResponseEntity.ok(shows);
	}

	/**
	 * 공연 목록 조회 API
	 */
	@GetMapping("/api/shows")
	public ResponseEntity<ShowResponse> getShows(@RequestParam(required = false) String word,
		@RequestParam(defaultValue = "1") @Min(value = 1) int page) {
		ShowResponse showResponse = showService.getShows(word, page);

		return ResponseEntity.ok(showResponse);
	}

	/**
	 * 공연 상세 조회 API
	 */
	@GetMapping("/api/shows/{showId}")
	public ResponseEntity<ShowDetailResponse> getShowDetail(@PathVariable Long showId) {
		ShowDetailResponse showDetailResponse = showService.getShowDetail(showId);

		return ResponseEntity.ok(showDetailResponse);
	}

	/**
	 * 공연 등록 API
	 */
	@PostMapping("/api/shows/{venueId}")
	public ResponseEntity<RegisterShowResponse> registerShow(@PathVariable Long venueId,
		@Valid @RequestBody RegisterShowRequest registerShowRequest) {
		RegisterShowResponse response = showService.registerShow(venueId, registerShowRequest);

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	/**
	 * 공연 수정 API
	 */
	@PutMapping("/api/shows/{showId}")
	public ResponseEntity<ShowDetailResponse> updateShow(@PathVariable Long showId,
		@Valid @RequestBody RegisterShowRequest request) {
		ShowDetailResponse response = showService.updateShow(showId, request);

		return ResponseEntity.ok(response);
	}

	/**
	 * 공연 삭제 API
	 */
	@DeleteMapping("/api/shows/{showId}")
	public ResponseEntity<Void> deleteShow(@PathVariable Long showId) {
		showService.deleteShow(showId);

		return ResponseEntity.noContent().build();
	}
}
