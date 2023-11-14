package kr.codesquad.jazzmeet.inquiry.dto.response;

import java.time.LocalDateTime;

public record InquiryAnswerUpdateResponse(
	Long id,
	String content,
	LocalDateTime createdAt,
	LocalDateTime modifiedAt
) {
}
