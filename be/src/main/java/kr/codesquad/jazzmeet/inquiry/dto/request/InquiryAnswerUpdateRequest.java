package kr.codesquad.jazzmeet.inquiry.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record InquiryAnswerUpdateRequest(
	@NotNull
	@Size(min = 1, max = 1000)
	String content
) {
}
