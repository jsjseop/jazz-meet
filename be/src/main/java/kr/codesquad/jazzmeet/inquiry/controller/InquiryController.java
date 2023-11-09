package kr.codesquad.jazzmeet.inquiry.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.constraints.Min;
import kr.codesquad.jazzmeet.inquiry.dto.response.InquiryDetailResponse;
import kr.codesquad.jazzmeet.inquiry.dto.response.InquirySearchResponse;
import kr.codesquad.jazzmeet.inquiry.service.InquiryService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class InquiryController {

	private final InquiryService inquiryService;

	/**
	 * 문의 글 목록 조회 API
	 */
	@GetMapping("/api/inquiries")
	public ResponseEntity<InquirySearchResponse> getInquiries(@RequestParam String category,
		@RequestParam(required = false) String word,
		@RequestParam @Min(value = 1) int page) {
		InquirySearchResponse inquiries = inquiryService.getInquiries(category, word, page);

		return ResponseEntity.ok(inquiries);
	}

	/**
	 * 문의 글 상세 조회 API
	 */
	@GetMapping("/api/inquiries/{inquiryId}")
	public ResponseEntity<?> getInquiryDetail(@PathVariable Long inquiryId) {
		InquiryDetailResponse inquiry = inquiryService.getInquiryDetail(inquiryId);

		return ResponseEntity.ok(inquiry);
	}
}
