package kr.codesquad.jazzmeet.inquiry.dto.request;

import jakarta.validation.constraints.NotNull;

public record InquiryAnswerSaveRequest(
	@NotNull
	Long inquiryId,
	@NotNull
	String content
) {
}
