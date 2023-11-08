package kr.codesquad.jazzmeet.inquiry.vo;

import java.time.LocalDateTime;

public record InquiryAnswerDetail(
	Long id,
	String content,
	LocalDateTime createdAt,
	LocalDateTime modifiedAt
) {
}
