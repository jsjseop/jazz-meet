package kr.codesquad.jazzmeet.global.error;

import org.springframework.core.NestedExceptionUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import kr.codesquad.jazzmeet.global.error.statuscode.ErrorCode;
import kr.codesquad.jazzmeet.global.error.statuscode.StatusCode;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	// CustomException 으로 발생한 예외 처리
	@ExceptionHandler(CustomException.class)
	protected ResponseEntity<ErrorResponse> handleCustomException(CustomException ex) {
		StatusCode statusCode = ex.getStatusCode();
		log.warn("CustomException handling: {}", ex.toString());
		return ResponseEntity.status(statusCode.getHttpStatus()).body(new ErrorResponse(statusCode.getMessage()));
	}

	// 500 에러
	// 데이터 베이스 오류
	@ExceptionHandler(DataAccessException.class)
	protected ResponseEntity<ErrorResponse> handleDataAccessException(DataAccessException ex) {
		ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR_DB;
		log.error("DataAccessException: ", ex);
		return ResponseEntity.status(errorCode.getHttpStatus())
			.body(new ErrorResponse(errorCode.getMessage(), ex.getMessage() + ", " + ex.getMostSpecificCause()));
	}

	// 서버 오류
	@ExceptionHandler(Exception.class)
	protected ResponseEntity<ErrorResponse> handleException(Exception ex) {
		ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
		log.error("Exception: ", ex);
		return ResponseEntity.status(errorCode.getHttpStatus())
			.body(new ErrorResponse(errorCode.getMessage(),
				ex.getMessage() + ", " + NestedExceptionUtils.getMostSpecificCause(ex)));
	}

	// @Valid 예외 처리
	@ExceptionHandler(MethodArgumentNotValidException.class)
	protected ResponseEntity<ErrorResponse> handleException(MethodArgumentNotValidException ex) {
		ErrorCode errorCode = ErrorCode.VALIDATION_FAILED;
		log.warn("MethodArgumentNotValidException handling: {}", ex.getMessage());
		return ResponseEntity.status(errorCode.getHttpStatus())
			.body(new ErrorResponse(errorCode.getMessage(),
				ex.getMessage() + ", " + NestedExceptionUtils.getMostSpecificCause(ex)));
	}
}
