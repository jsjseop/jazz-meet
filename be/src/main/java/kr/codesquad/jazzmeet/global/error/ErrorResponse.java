package kr.codesquad.jazzmeet.global.error;

import lombok.Getter;

@Getter
public class ErrorResponse {
	// ResponseEntity의 body에 담기 위해 사용
	private final String errorMessage;

	public ErrorResponse(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
