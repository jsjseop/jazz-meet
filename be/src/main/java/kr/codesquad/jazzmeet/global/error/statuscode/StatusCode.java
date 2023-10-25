package kr.codesquad.jazzmeet.global.error.statuscode;

import org.springframework.http.HttpStatus;

public interface StatusCode {
	String getName();

	HttpStatus getHttpStatus();

	String getMessage();
}
