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
	NOT_FOUND_DAY_OF_WEEK(HttpStatus.NOT_FOUND, "해당하는 요일이 없습니다.");

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
