package kr.codesquad.jazzmeet.global.error.statuscode;

import org.springframework.http.HttpStatus;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ShowErrorCode implements StatusCode {

	NOT_VALID_DATE_FORMAT(HttpStatus.BAD_REQUEST, "날짜 포맷이 잘못되었습니다. 'yyyyMMdd' 형식으로 입력해주세요");

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
