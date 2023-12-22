package kr.codesquad.jazzmeet.global.error.statuscode;

import org.springframework.http.HttpStatus;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ErrorCode implements StatusCode {
	// 예시. 필요한 에러가 있을 때 마다 구현체를 추가해서 사용합니다.
	// 400 Bad Request
	VALIDATION_FAILED(HttpStatus.BAD_REQUEST, "유효하지 않은 형식입니다."),
	TYPE_MISMATCH(HttpStatus.BAD_REQUEST, "유효하지 않은 타입입니다."),
	WRONG_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
	// 500 Internal Server Error
	INTERNAL_SERVER_ERROR_DB(HttpStatus.INTERNAL_SERVER_ERROR, "데이터베이스 에러입니다."),
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러입니다."),

	// -- [JWT] -- //
	MALFORMED_JWT_EXCEPTION(HttpStatus.UNAUTHORIZED, "잘못된 형식의 토큰입니다."),
	EXPIRED_JWT_EXCEPTION(HttpStatus.UNAUTHORIZED, "만료된 토큰입니다."),
	SIGNATURE_EXCEPTION(HttpStatus.UNAUTHORIZED, "JWT의 서명이 올바르지 않습니다."),
	UNSUPPORTED_JWT_EXCEPTION(HttpStatus.UNAUTHORIZED, "지원하지 않는 토큰입니다."),
	ILLEGAL_ARGUMENT_EXCEPTION(HttpStatus.UNAUTHORIZED, "잘못된 인자입니다."),
	UNKNOWN_EXCEPTION(HttpStatus.UNAUTHORIZED, "알 수 없는 오류가 발생했습니다." ),
	INVALID_TOKEN(HttpStatus.BAD_REQUEST, "잘못된 토큰입니다."),
	UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED, "권한이 없습니다."),
	TOKEN_MISSING(HttpStatus.BAD_REQUEST, "토큰이 누락되었습니다."),
	INVALID_TOKEN_FORMAT(HttpStatus.BAD_REQUEST, "토큰 헤더의 형식이 잘못되었습니다.");

	private final HttpStatus httpStatus;
	private final String message;

	public static StatusCode from(RuntimeException e) {
		if (e instanceof MalformedJwtException) {
			return ErrorCode.MALFORMED_JWT_EXCEPTION;
		}
		if (e instanceof ExpiredJwtException) {
			return ErrorCode.EXPIRED_JWT_EXCEPTION;
		}
		if (e instanceof SignatureException) {
			return ErrorCode.SIGNATURE_EXCEPTION;
		}
		if (e instanceof UnsupportedJwtException) {
			return ErrorCode.UNSUPPORTED_JWT_EXCEPTION;
		}
		if (e instanceof IllegalArgumentException) {
			return ErrorCode.ILLEGAL_ARGUMENT_EXCEPTION;
		}
		return ErrorCode.UNKNOWN_EXCEPTION;
	}

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
