package kr.codesquad.jazzmeet.inquiry.dto.request;

import jakarta.validation.constraints.NotNull;

public record InquiryAnswerUpdateRequest(
	@NotNull
	String content
) {
}
