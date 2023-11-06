package kr.codesquad.jazzmeet.inquiry.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum InquiryCategory {
	SERVICE("서비스"),
	REGISTRATION("등록"),
	ETC("기타");

	private final String name;

}
