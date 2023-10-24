package kr.codesquad.jazzmeet.global.error;

import lombok.Getter;

@Getter
public class ErrorResponse {
	// ResponseEntity의 body에 담기 위해 사용
	private String message;
	private String detail;

	public ErrorResponse(String message, String detail) {
		this.message = message;
		this.detail = detail;
	}

	public ErrorResponse(String message) {
		this.message = message;
	}
}
