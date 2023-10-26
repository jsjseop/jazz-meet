package kr.codesquad.jazzmeet.venue.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.codesquad.jazzmeet.venue.dto.response.VenueAutocompleteResponse;
import kr.codesquad.jazzmeet.venue.service.VenueService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class VenueController {

	private final VenueService venueService;

	@GetMapping("/api/search")
	public ResponseEntity<List<VenueAutocompleteResponse>> searchAutocompleteList(@RequestParam String word) {
		return ResponseEntity.ok().body(venueService.searchAutocompleteList(word));
	}

	@GetMapping("/")
	public ResponseEntity<?> getHello() {
		return ResponseEntity.ok().body("아뇽!");
	}
}
