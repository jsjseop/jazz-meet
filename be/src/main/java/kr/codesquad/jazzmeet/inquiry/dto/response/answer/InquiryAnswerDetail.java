package kr.codesquad.jazzmeet.inquiry.dto.response.answer;

import java.time.LocalDateTime;

public record InquiryAnswerDetail(
	Long id,
	String content,
	LocalDateTime createdAt,
	LocalDateTime modifiedAt
) {
}
