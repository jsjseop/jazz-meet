package kr.codesquad.jazzmeet.inquiry.util;

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
		for (InquiryCategory category : values()) {
			if (category.getKoName().equals(koName)) {
				return category;
			}
		}
		throw new CustomException(InquiryErrorCode.NO_MATCH_VALUE);
	}
}
