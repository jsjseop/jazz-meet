package kr.codesquad.jazzmeet.review.dto.response;

import java.time.LocalDateTime;

public record ReviewCreateResponse(

	Long id,
	String nickname,
	String content,
	LocalDateTime createdAt
) {
}
