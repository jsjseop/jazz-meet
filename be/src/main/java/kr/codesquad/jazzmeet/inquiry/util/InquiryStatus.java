package kr.codesquad.jazzmeet.inquiry.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum InquiryStatus {
	WAITING("검토중"),
	REPLIED("답변완료");

	private final String name;

}
