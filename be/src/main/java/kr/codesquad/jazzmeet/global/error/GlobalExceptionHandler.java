package kr.codesquad.jazzmeet.global.error;

import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import jakarta.validation.ValidationException;
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

	// 500 INTERNAL SERVER ERROR
	// 데이터 베이스 오류
	@ExceptionHandler(DataAccessException.class)
	protected ResponseEntity<ErrorResponse> handleDataAccessException(DataAccessException ex) {
		ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR_DB;
		log.error("DataAccessException: ", ex);
		return ResponseEntity.status(errorCode.getHttpStatus())
			.body(new ErrorResponse(errorCode.getMessage()));
	}

	// 서버 오류
	@ExceptionHandler(Exception.class)
	protected ResponseEntity<ErrorResponse> handleException(Exception ex) {
		ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
		log.error("Exception: ", ex);
		return ResponseEntity.status(errorCode.getHttpStatus())
			.body(new ErrorResponse(errorCode.getMessage()));
	}

	// 400 BAD REQUEST
	// @Valid 검증 실패
	@ExceptionHandler({MethodArgumentNotValidException.class, ValidationException.class})
	protected ResponseEntity<ErrorResponse> handleValidateException(Exception ex) {
		ErrorCode errorCode = ErrorCode.VALIDATION_FAILED;
		log.warn("ValidException handling: {}", ex.getMessage());
		return ResponseEntity.status(errorCode.getHttpStatus())
			.body(new ErrorResponse(errorCode.getMessage()));
	}

	// 400 BAD REQUEST
	// 타입 불일치
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	protected ResponseEntity<ErrorResponse> handleRequestFailException(Exception ex) {
		ErrorCode errorCode = ErrorCode.TYPE_MISMATCH;
		log.warn("MethodArgumentTypeMismatchException handling: {}", ex.getMessage());
		return ResponseEntity.status(errorCode.getHttpStatus())
			.body(new ErrorResponse(errorCode.getMessage()));
	}
}
