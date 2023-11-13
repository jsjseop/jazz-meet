package kr.codesquad.jazzmeet.inquiry.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record InquirySaveRequest(
	@NotNull
	String category,
	@Size(max = 8)
	@NotNull
	String nickname,
	@Size(max = 20)
	@NotNull
	String password,
	@NotNull
	String content
) {
}
