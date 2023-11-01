package kr.codesquad.jazzmeet.venue.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.codesquad.jazzmeet.venue.dto.response.NearbyVenueResponse;
import kr.codesquad.jazzmeet.venue.dto.response.VenueAutocompleteResponse;
import kr.codesquad.jazzmeet.venue.dto.response.VenuePinsResponse;
import kr.codesquad.jazzmeet.venue.service.VenueService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class VenueController {

	private final VenueService venueService;

	@GetMapping("/api/search")
	public ResponseEntity<List<VenueAutocompleteResponse>> searchAutocompleteList(@RequestParam String word) {
		List<VenueAutocompleteResponse> venues = venueService.searchAutocompleteList(word);
		return ResponseEntity.ok(venues);
	}

	@GetMapping("/api/venues/around-venues")
	public ResponseEntity<List<NearbyVenueResponse>> findNearbyVenues(
		@RequestParam Double latitude,
		@RequestParam Double longitude) {
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
	public ResponseEntity<List<VenuePinsResponse>> findVenuePinsByLocation(@RequestParam Double lowLatitude,
		@RequestParam Double highLatitude, @RequestParam Double lowLongitude, @RequestParam Double highLongitude) {
		List<VenuePinsResponse> venuePins = venueService.findVenuePinsByLocation(lowLatitude, highLatitude, lowLongitude, highLongitude);
		return ResponseEntity.ok(venuePins);
	}
}
