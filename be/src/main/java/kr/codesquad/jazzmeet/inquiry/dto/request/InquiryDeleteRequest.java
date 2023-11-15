package kr.codesquad.jazzmeet.inquiry.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record InquiryDeleteRequest(
	@Size(min = 4, max = 20)
	@NotNull
	String password
) {
}
