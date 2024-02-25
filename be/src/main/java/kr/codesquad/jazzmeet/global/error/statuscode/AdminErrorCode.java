package kr.codesquad.jazzmeet.global.error.statuscode;

import org.springframework.http.HttpStatus;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum AdminErrorCode implements StatusCode {
	NOT_FOUND_ADMIN(HttpStatus.NOT_FOUND, "해당하는 계정을 찾을 수 없습니다."),
	ALREADY_EXIST_ADMIN(HttpStatus.BAD_REQUEST, "이미 존재하는 로그인 아이디입니다."),
	UNAUTHORIZED_ROLE(HttpStatus.UNAUTHORIZED, "루트 관리자 계정이 아닙니다."),
	NOT_EXIST_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "Refresh Token이 일치하지 않습니다." ),
	NOT_EXIST_COOKIE(HttpStatus.BAD_REQUEST, "쿠키가 전혀 존재하지 않습니다." ),
	NOT_FOUND_TOKEN_COOKIE(HttpStatus.UNAUTHORIZED, "필요한 refresh token 쿠키를 찾을 수 없습니다." );

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
