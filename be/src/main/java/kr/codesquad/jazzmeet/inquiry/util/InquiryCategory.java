package kr.codesquad.jazzmeet.inquiry.util;

import java.util.Arrays;

import kr.codesquad.jazzmeet.global.error.CustomException;
import kr.codesquad.jazzmeet.global.error.statuscode.InquiryErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum InquiryCategory {
	SERVICE("서비스"),
	REGISTRATION("등록"),
	ETC("기타");

	private final String koName;

	public static InquiryCategory toInquiryCategory(String koName) {
		return Arrays.stream(values())
			.filter(category -> category.getKoName().equals(koName))
			.findFirst()
			.orElseThrow(() -> new CustomException(InquiryErrorCode.NO_MATCH_VALUE));
	}
}
