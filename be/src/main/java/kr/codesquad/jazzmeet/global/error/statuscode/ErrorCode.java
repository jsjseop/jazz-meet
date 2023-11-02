package kr.codesquad.jazzmeet.global.error.statuscode;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ErrorCode implements StatusCode {
	// 예시. 필요한 에러가 있을 때 마다 구현체를 추가해서 사용합니다.
	// 400 Bad Request
	VALIDATION_FAILED(HttpStatus.BAD_REQUEST, "유효하지 않은 형식입니다."),
	// 500 Internal Server Error
	INTERNAL_SERVER_ERROR_DB(HttpStatus.INTERNAL_SERVER_ERROR, "데이터베이스 에러입니다."),
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러입니다."),

	// -- [Venue] -- //
	NOT_FOUND_VENUE(HttpStatus.NOT_FOUND, "해당하는 공연장이 없습니다."),

	// -- [Show] -- //
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
