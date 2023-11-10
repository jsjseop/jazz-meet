package kr.codesquad.jazzmeet.inquiry.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import kr.codesquad.jazzmeet.global.error.CustomException;
import kr.codesquad.jazzmeet.global.error.statuscode.InquiryErrorCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class EncryptPasswordEncoder {

	@Value("${encrypt.secret.salt}")
	private String salt;

	public String encode(String pwd) {
		String result = null;
		try {
			// 1. SHA256 알고리즘 객체 생성
			MessageDigest md = MessageDigest.getInstance("SHA-256");

			// 2. pwd 와 salt 합친 문자열에 SHA256 적용
			md.update((pwd + salt).getBytes());
			byte[] saltedPwd = md.digest();

			// 3. byte to String (10진수 문자열로 변경)
			StringBuffer sb = new StringBuffer();
			for (byte b : saltedPwd) {
				sb.append(String.format("%02x", b));
			}

			result = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			throw new CustomException(InquiryErrorCode.NO_SUCH_ALGORITHM);
		}
		return result;
	}

}

