package kr.codesquad.jazzmeet.venue.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import kr.codesquad.jazzmeet.admin.entity.Admin;
import kr.codesquad.jazzmeet.global.permission.AdminAuth;
import kr.codesquad.jazzmeet.global.permission.Permission;
import kr.codesquad.jazzmeet.venue.controller.annotation.Latitude;
import kr.codesquad.jazzmeet.venue.controller.annotation.Longitude;
import kr.codesquad.jazzmeet.venue.dto.request.RangeCoordinatesRequest;
import kr.codesquad.jazzmeet.venue.dto.request.VenueCreateRequest;
import kr.codesquad.jazzmeet.venue.dto.request.VenueUpdateRequest;
import kr.codesquad.jazzmeet.venue.dto.response.NearbyVenueResponse;
import kr.codesquad.jazzmeet.venue.dto.response.VenueAutocompleteResponse;
import kr.codesquad.jazzmeet.venue.dto.response.VenueCreateResponse;
import kr.codesquad.jazzmeet.venue.dto.response.VenueDetailResponse;
import kr.codesquad.jazzmeet.venue.dto.response.VenueListResponse;
import kr.codesquad.jazzmeet.venue.dto.response.VenuePinsResponse;
import kr.codesquad.jazzmeet.venue.dto.response.VenueSearchResponse;
import kr.codesquad.jazzmeet.venue.service.VenueFacade;
import kr.codesquad.jazzmeet.venue.service.VenueService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Validated
@RestController
public class VenueController {

	private final VenueService venueService;
	private final VenueFacade venueFacade;

	/**
	 * 검색어 자동완성 목록 조회 API
	 */
	@GetMapping("/api/search")
	public ResponseEntity<List<VenueAutocompleteResponse>> searchAutocompleteList(@RequestParam String word) {
		List<VenueAutocompleteResponse> venues = venueService.searchAutocompleteList(word);

		return ResponseEntity.ok(venues);
	}

	/**
	 * 주변 공연장 목록 조회 API
	 */
	@GetMapping("/api/venues/around-venues")
	public ResponseEntity<List<NearbyVenueResponse>> findNearbyVenues(
		@RequestParam(required = false) @Latitude Double latitude,
		@RequestParam(required = false) @Longitude Double longitude) {
		List<NearbyVenueResponse> nearByVenues = venueService.findNearByVenues(latitude, longitude);

		return ResponseEntity.ok(nearByVenues);
	}

	/**
	 * 공연장 위치 정보(핀) 목록 조회 - 검색 API
	 */
	@GetMapping("/api/venues/pins/search")
	public ResponseEntity<List<VenuePinsResponse>> findVenuePinsBySearch(@RequestParam(required = false) String word) {
		List<VenuePinsResponse> venuePins = venueService.findVenuePinsBySearch(word);

		return ResponseEntity.ok(venuePins);
	}

	/**
	 * 공연장 위치 정보(핀) 목록 조회 - 지도 기반 API
	 */
	@GetMapping("/api/venues/pins/map")
	public ResponseEntity<List<VenuePinsResponse>> findVenuePinsByLocation(
		@ModelAttribute @Valid RangeCoordinatesRequest rangeCoordinatesRequest) {
		List<VenuePinsResponse> venuePins = venueService.findVenuePinsByLocation(rangeCoordinatesRequest);

		return ResponseEntity.ok(venuePins);
	}

	/**
	 * 공연장 목록 조회 - 지도 기반 API
	 */
	@GetMapping("/api/venues/map")
	public ResponseEntity<VenueSearchResponse> findVenuesByLocation(
		@ModelAttribute RangeCoordinatesRequest rangeCoordinatesRequest,
		@RequestParam(defaultValue = "1") @Min(value = 1) int page
	) {
		VenueSearchResponse venueResponse = venueService.findVenuesByLocation(rangeCoordinatesRequest, page);

		return ResponseEntity.ok(venueResponse);
	}

	/**
	 * 공연장 상세 조회 API
	 */
	@GetMapping("/api/venues/{venueId}")
	public ResponseEntity<VenueDetailResponse> findVenue(@PathVariable Long venueId) {
		VenueDetailResponse venue = venueService.findVenue(venueId);

		return ResponseEntity.ok(venue);
	}

	/**
	 * 공연장 목록 조회 - 검색 API
	 */
	@GetMapping("/api/venues/search")
	public ResponseEntity<VenueSearchResponse> searchVenueList(
		@RequestParam(required = false) String word, @RequestParam(defaultValue = "1") @Min(value = 1) int page) {
		VenueSearchResponse venuesResponse = venueService.searchVenueList(word, page);

		return ResponseEntity.ok(venuesResponse);
	}

	/**
	 * 공연장 목록 조회 - 공연장 하나만 검색 API
	 */
	@GetMapping("/api/venues/search/{venueId}")
	public ResponseEntity<VenueSearchResponse> searchVenueListById(@PathVariable Long venueId) {
		VenueSearchResponse venueResponse = venueService.findVenueSearchById(venueId);

		return ResponseEntity.ok(venueResponse);
	}

	/**
	 * 공연장 목록 조회 API
	 */
	@GetMapping("/api/venues")
	public ResponseEntity<VenueListResponse> findVenues(@RequestParam(required = false) String word,
		@RequestParam(defaultValue = "1") @Min(value = 1) int page) {
		VenueListResponse venues = venueService.findVenuesByWord(word, page);

		return ResponseEntity.ok(venues);
	}

	/**
	 * 공연장 등록 API
	 */
	@Permission
	@PostMapping("/api/venues")
	public ResponseEntity<VenueCreateResponse> createVenue(@AdminAuth Admin admin, @RequestBody @Valid VenueCreateRequest venueCreateRequest) {
		VenueCreateResponse venueCreateResponse = venueFacade.createVenue(venueCreateRequest, admin);

		return ResponseEntity.status(HttpStatus.CREATED).body(venueCreateResponse);
	}

	/**
	 * 공연장 수정 API
	 */
	@Permission
	@PutMapping("/api/venues/{venueId}")
	public ResponseEntity<VenueDetailResponse> updateVenue(@RequestBody @Valid VenueUpdateRequest venueUpdateRequest,
		@PathVariable Long venueId) {
		VenueDetailResponse venueDetailResponse = venueFacade.updateVenue(venueUpdateRequest, venueId);

		return ResponseEntity.ok(venueDetailResponse);
	}

	/**
	 * 공연장 삭제 API
	 */
	@Permission
	@DeleteMapping("/api/venues/{venueId}")
	public ResponseEntity<Void> deleteVenue(@PathVariable Long venueId) {
		venueFacade.deleteVenue(venueId);

		return ResponseEntity.noContent().build();
	}
}
