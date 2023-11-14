package kr.codesquad.jazzmeet.inquiry.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record InquirySaveRequest(
	@NotNull
	String category,
	@Size(min = 2, max = 20)
	@NotNull
	String nickname,
	@Size(min = 4, max = 20)
	@NotNull
	String password,
	@NotNull
	@Size(min = 1, max = 500)
	String content
) {
}
