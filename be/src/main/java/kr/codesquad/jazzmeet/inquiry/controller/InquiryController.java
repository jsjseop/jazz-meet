package kr.codesquad.jazzmeet.inquiry.controller;

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
import kr.codesquad.jazzmeet.inquiry.dto.request.InquiryDeleteRequest;
import kr.codesquad.jazzmeet.inquiry.dto.request.InquirySaveRequest;
import kr.codesquad.jazzmeet.inquiry.dto.request.answer.InquiryAnswerSaveRequest;
import kr.codesquad.jazzmeet.inquiry.dto.request.answer.InquiryAnswerUpdateRequest;
import kr.codesquad.jazzmeet.inquiry.dto.response.InquiryDetailResponse;
import kr.codesquad.jazzmeet.inquiry.dto.response.InquirySaveResponse;
import kr.codesquad.jazzmeet.inquiry.dto.response.InquirySearchResponse;
import kr.codesquad.jazzmeet.inquiry.dto.response.answer.InquiryAnswerSaveResponse;
import kr.codesquad.jazzmeet.inquiry.dto.response.answer.InquiryAnswerUpdateResponse;
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
	public ResponseEntity<InquirySearchResponse> getInquiries(
		@RequestParam(defaultValue = "서비스") String category,
		@RequestParam(required = false) String word,
		@RequestParam(defaultValue = "1") @Min(value = 1) int page) {
		InquirySearchResponse inquiries = inquiryService.getInquiries(category, word, page);

		return ResponseEntity.ok(inquiries);
	}

	/**
	 * 문의 글 상세 조회 API
	 */
	@GetMapping("/api/inquiries/{inquiryId}")
	public ResponseEntity<InquiryDetailResponse> getInquiryDetail(@PathVariable Long inquiryId) {
		InquiryDetailResponse inquiry = inquiryService.getInquiryDetail(inquiryId);

		return ResponseEntity.ok(inquiry);
	}

	/**
	 * 문의 글 등록 API
	 */
	@PostMapping("/api/inquiries")
	public ResponseEntity<InquirySaveResponse> save(@RequestBody @Valid InquirySaveRequest inquirySaveRequest) {
		InquirySaveResponse inquiry = inquiryService.save(inquirySaveRequest);

		return ResponseEntity.status(HttpStatus.CREATED).body(inquiry);
	}

	/**
	 * 문의 글 삭제 API
	 */
	@DeleteMapping("/api/inquiries/{inquiryId}")
	public ResponseEntity<Void> delete(@PathVariable Long inquiryId,
		@RequestBody @Valid InquiryDeleteRequest inquiryDeleteRequest) {
		inquiryService.delete(inquiryId, inquiryDeleteRequest);

		return ResponseEntity.noContent().build();
	}

	/**
	 * 문의 답변 등록 API
	 */
	@PostMapping("/api/inquiries/answers")
	public ResponseEntity<InquiryAnswerSaveResponse> saveAnswer(@RequestBody @Valid InquiryAnswerSaveRequest request) {
		InquiryAnswerSaveResponse answer = inquiryService.saveAnswer(request);

		return ResponseEntity.status(HttpStatus.CREATED).body(answer);
	}

	/**
	 * 문의 답변 수정 API
	 */
	@PutMapping("/api/inquiries/answers/{answerId}")
	public ResponseEntity<InquiryAnswerUpdateResponse> updateAnswer(@PathVariable Long answerId,
		@RequestBody @Valid InquiryAnswerUpdateRequest request) {
		InquiryAnswerUpdateResponse answer = inquiryService.updateAnswer(answerId, request);
		return ResponseEntity.ok(answer);
	}

	/**
	 * 문의 답변 삭제 API
	 */
	@DeleteMapping("/api/inquiries/answers/{answerId}")
	public ResponseEntity<Void> deleteAnswer(@PathVariable Long answerId) {
		// TODO: 로그인 구현 시, token과 answer의 adminId가 같은지 검증 후 삭제 가능.
		inquiryService.deleteAnswer(answerId);
		return ResponseEntity.noContent().build();
	}
}
