package kr.codesquad.jazzmeet.inquiry.dto.response;

import java.time.LocalDateTime;

public record InquirySaveResponse(
	Long id,
	String status,
	String content,
	String nickname,
	LocalDateTime createdAt
) {
}
