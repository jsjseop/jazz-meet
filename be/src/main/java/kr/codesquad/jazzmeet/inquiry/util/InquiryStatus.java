package kr.codesquad.jazzmeet.inquiry.util;

import kr.codesquad.jazzmeet.global.error.CustomException;
import kr.codesquad.jazzmeet.global.error.statuscode.EnumErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum InquiryStatus {
	WAITING("검토중"),
	REPLIED("답변완료");

	private final String koName;

	public static InquiryStatus toInquiryStatus(String koName) {
		for (InquiryStatus inquiryStatus : values()) {
			if (inquiryStatus.getKoName().equals(koName)) {
				return inquiryStatus;
			}
		}
		throw new CustomException(EnumErrorCode.NO_MATCH_VALUE);
	}

}
