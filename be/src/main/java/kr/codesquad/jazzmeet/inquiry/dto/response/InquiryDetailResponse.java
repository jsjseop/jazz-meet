package kr.codesquad.jazzmeet.inquiry.dto.response;

import java.time.LocalDateTime;

import kr.codesquad.jazzmeet.inquiry.dto.response.answer.InquiryAnswerDetail;

public record InquiryDetailResponse(
	Long id,
	String status,
	String content,
	String nickname,
	LocalDateTime createdAt,
	InquiryAnswerDetail answer
) {
}
