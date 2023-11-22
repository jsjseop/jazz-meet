package kr.codesquad.jazzmeet.admin.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record SignUpAdminRequest(
	@NotNull
	@Size(min = 5, max = 20, message = "아이디는 5자 이상, 20자 이하여야 합니다.")
	String loginId,
	@NotNull
	@Size(min = 5, max = 20, message = "비밀번호는 5자 이상, 20자 사이여야 합니다.")
	String password
) {
}
