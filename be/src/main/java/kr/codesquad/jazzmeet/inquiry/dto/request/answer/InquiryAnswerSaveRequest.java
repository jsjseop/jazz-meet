package kr.codesquad.jazzmeet.inquiry.dto.request.answer;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record InquiryAnswerSaveRequest(
	@NotNull
	Long inquiryId,
	@NotNull
	@Size(min = 1, max = 1000)
	String content
) {
}
