package kr.codesquad.jazzmeet.inquiry.dto.response.answer;

import java.time.LocalDateTime;

public record InquiryAnswerSaveResponse(
	Long id,
	String content,
	LocalDateTime createdAt
) {
}
