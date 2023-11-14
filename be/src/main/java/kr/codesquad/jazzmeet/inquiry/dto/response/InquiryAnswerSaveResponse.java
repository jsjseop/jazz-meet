package kr.codesquad.jazzmeet.inquiry.dto.response;

import java.time.LocalDateTime;

public record InquiryAnswerSaveResponse(
	Long id,
	String content,
	LocalDateTime createdAt
) {
}
