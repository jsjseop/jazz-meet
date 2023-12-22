package kr.codesquad.jazzmeet.admin.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record ReissueAdminRequest(
	@NotNull
	String refreshToken
) {
}
