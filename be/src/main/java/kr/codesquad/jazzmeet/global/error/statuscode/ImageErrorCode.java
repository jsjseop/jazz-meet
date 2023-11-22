package kr.codesquad.jazzmeet.global.error.statuscode;

import org.springframework.http.HttpStatus;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ImageErrorCode implements StatusCode{

	// -- [Image] -- //
	IMAGE_UPLOAD_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "이미지 업로드에 실패했습니다."),
	IMAGE_DELETE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "이미지 삭제에 실패했습니다."),
	NOT_FOUND_IMAGE(HttpStatus.NOT_FOUND, "해당하는 이미지가 없습니다."),
	WRONG_IMAGE_FORMAT(HttpStatus.BAD_REQUEST, "잘못된 파일 형식입니다."),
	IMAGE_LIMIT_EXCEEDED(HttpStatus.BAD_REQUEST, "이미지는 최대 10개까지 등록할 수 있습니다.");

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
