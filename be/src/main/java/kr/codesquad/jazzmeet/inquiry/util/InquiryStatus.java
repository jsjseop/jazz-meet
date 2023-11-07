package kr.codesquad.jazzmeet.inquiry.util;

import java.util.Arrays;

import kr.codesquad.jazzmeet.global.error.CustomException;
import kr.codesquad.jazzmeet.global.error.statuscode.InquiryErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum InquiryStatus {
	WAITING("검토중"),
	REPLIED("답변완료");

	private final String koName;

	public static InquiryStatus toInquiryStatus(String koName) {
		return Arrays.stream(values())
			.filter(status -> status.getKoName().equals(koName))
			.findFirst()
			.orElseThrow(() -> new CustomException(InquiryErrorCode.NO_MATCH_VALUE));
	}

}
