package kr.codesquad.jazzmeet.address.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.constraints.Min;
import kr.codesquad.jazzmeet.address.dto.response.AddressSearchResponse;
import kr.codesquad.jazzmeet.address.service.AddressService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class AddressController {
	private final AddressService addressService;

	/**
	 * 주소 검색 API
	 */
	@GetMapping("/api/geocode")
	public ResponseEntity<AddressSearchResponse> search(@RequestParam String word,
		@RequestParam(defaultValue = "1") @Min(value = 1) int page) {
		AddressSearchResponse response = addressService.search(word, page);

		return ResponseEntity.ok(response);
	}
}
