package kr.codesquad.jazzmeet.global.error.statuscode;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum InquiryErrorCode implements StatusCode {
	NO_MATCH_VALUE(HttpStatus.NOT_FOUND, "해당하는 종류가 없습니다."),
	NOT_FOUND_INQUIRY(HttpStatus.NOT_FOUND, "해당하는 문의가 없습니다."),
	NO_SUCH_ALGORITHM(HttpStatus.NOT_FOUND, "해당하는 알고리즘이 없습니다.");

	private final HttpStatus httpStatus;
	private final String message;

	@Override
	public String getName() {
		return name();
	}

	@Override
	public HttpStatus getHttpStatus() {
		return this.httpStatus;
	}

	@Override
	public String getMessage() {
		return this.message;
	}
}
