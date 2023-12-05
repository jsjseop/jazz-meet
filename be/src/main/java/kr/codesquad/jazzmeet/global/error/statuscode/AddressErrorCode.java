package kr.codesquad.jazzmeet.global.error.statuscode;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum AddressErrorCode implements StatusCode {
	// 500 Internal Server Error
	REQUEST_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "주소 요청이 실패했습니다."),
	WORD_IS_EMPTY(HttpStatus.BAD_REQUEST, "검색어를 입력해주세요.");

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
