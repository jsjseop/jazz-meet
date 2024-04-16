package kr.codesquad.jazzmeet.global.error.statuscode;

import org.springframework.http.HttpStatus;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ShowErrorCode implements StatusCode {

	NOT_VALID_DATE_FORMAT(HttpStatus.BAD_REQUEST, "날짜 포맷이 잘못되었습니다. 'yyyyMMdd' 형식으로 입력해주세요"),
	NOT_FOUND_SHOW(HttpStatus.NOT_FOUND, "해당하는 공연이 없습니다."),
	// 웹 크롤링 실패
	CRAWLING_REQUEST_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "이미지 URL 추출에 실패했습니다."),
	NOT_FOUND_SHOW_IMAGE_URL(HttpStatus.EXPECTATION_FAILED, "해당하는 이미지 url을 찾지 못했습니다."),
	NOT_FOUND_SHOW_DATE(HttpStatus.EXPECTATION_FAILED, "공연 정보에서 공연 날짜를 찾지 못했습니다."),
	// OCR 실패
	OCR_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "OCR이 실패했습니다."),
	OCR_NOT_MATCHED_VENUE_AND_IMAGE(HttpStatus.INTERNAL_SERVER_ERROR, "자동 인식 된 OCR 템플릿이 공연장과 일치하지 않습니다."),
	OCR_EXTRACTION_FAILED_SHOW_DATE(HttpStatus.INTERNAL_SERVER_ERROR, "OCR 공연 날짜 인식 및 추출에 실패했습니다."),
	OCR_EXTRACTION_FAILED_ARTISTS_AND_DETAILS(HttpStatus.INTERNAL_SERVER_ERROR, "OCR 아티스트와 상세 정보 인식 및 추출에 실패했습니다."),
	OCR_NOT_LATEST_SHOW(HttpStatus.EXPECTATION_FAILED, "OCR로 추출한 날짜가 공연의 최신 날짜가 아닙니다."),
	OBJECT_TRANSFORMATION_FAILD(HttpStatus.EXPECTATION_FAILED, "객체 변환에 실패했습니다. (JSON -> RegisterShowRequest)"),
	OCR_NOT_EQUAL_TEAMS_AND_POSTER_NUMBERS(HttpStatus.EXPECTATION_FAILED, "팀의 수와 포스터의 수가 일치하지 않습니다."),
	OCR_IMAGE_SAVE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "이미지 url를 파일로 변환하는데 실패했습니다."),
	OCR_NOT_FOUND_DATE(HttpStatus.INTERNAL_SERVER_ERROR, "OCR로 추출한 텍스트에 공연 날짜가 없습니다."),
	OCR_NOT_FOUND_MATCHED_TEMPLATE(HttpStatus.INTERNAL_SERVER_ERROR, "OCR에 등록된 템플릿과 일치하는 템플릿이 없습니다.");

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
