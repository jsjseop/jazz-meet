package kr.codesquad.jazzmeet.global.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import kr.codesquad.jazzmeet.global.error.CustomException;
import kr.codesquad.jazzmeet.global.error.statuscode.ErrorCode;

@Component
public class PasswordEncoder {

	private static BCryptPasswordEncoder encoder;

	public PasswordEncoder(BCryptPasswordEncoder encoder) {
		PasswordEncoder.encoder = encoder;
	}

	public static void matchesPassword(String rawPassword, String encodedPassword) {
		boolean isMatched = encoder.matches(rawPassword, encodedPassword);
		if (!isMatched) {
			throw new CustomException(ErrorCode.WRONG_PASSWORD);
		}
	}

	public static String encode(String password) {
		return encoder.encode(password);
	}

}
