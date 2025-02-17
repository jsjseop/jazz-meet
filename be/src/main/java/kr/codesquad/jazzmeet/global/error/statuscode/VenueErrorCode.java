package kr.codesquad.jazzmeet.global.error.statuscode;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum VenueErrorCode implements StatusCode {

	// -- [Venue] -- //
	INVALID_LOCATION_ERROR(HttpStatus.BAD_REQUEST, "잘못된 위치 정보입니다."),
	NOT_FOUND_VENUE(HttpStatus.NOT_FOUND, "해당하는 공연장이 없습니다."),

	// -- [LinkType] -- //
	NOT_FOUND_LINK_TYPE(HttpStatus.NOT_FOUND, "해당하는 링크 타입이 없습니다."),

	// -- [DayOfWeek] -- //
	NOT_FOUND_DAY_OF_WEEK(HttpStatus.NOT_FOUND, "해당하는 요일이 없습니다."),

	// -- [Review] -- //
	NOT_FOUND_REVIEW(HttpStatus.NOT_FOUND, "해당하는 리뷰가 없습니다."),
	UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "권한이 없습니다."),
	NO_MATCH_VALUES(HttpStatus.NOT_FOUND, "해당하는 종류가 없습니다."),
	ALREADY_EXIST_REACTION(HttpStatus.BAD_REQUEST, "이미 반응을 했습니다."),
	NOT_FOUND_REACTION(HttpStatus.NOT_FOUND, "해당하는 반응이 없습니다.");

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
