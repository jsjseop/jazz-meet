package kr.codesquad.jazzmeet.fixture;

import java.time.LocalDateTime;

import kr.codesquad.jazzmeet.inquiry.entity.Inquiry;
import kr.codesquad.jazzmeet.inquiry.util.InquiryCategory;
import kr.codesquad.jazzmeet.inquiry.util.InquiryStatus;

public class InquiryFixture {

	public static Inquiry createInquiry(InquiryCategory category) {
		return Inquiry.builder()
			.nickname("닉네임")
			.password("비밀번호")
			.content("내용")
			.category(category)
			.status(
				InquiryStatus.WAITING)
			.createdAt(LocalDateTime.now())
			.build();
	}

	public static Inquiry createInquiry(InquiryCategory category, String nickname) {
		return Inquiry.builder()
			.nickname(nickname)
			.password("비밀번호")
			.content("내용")
			.category(category)
			.status(
				InquiryStatus.WAITING)
			.createdAt(LocalDateTime.now())
			.build();
	}

	public static Inquiry createInquiry(String content, InquiryCategory category) {
		return Inquiry.builder()
			.nickname("닉네임")
			.password("비밀번호")
			.content(content)
			.category(category)
			.status(
				InquiryStatus.WAITING)
			.createdAt(LocalDateTime.now())
			.build();
	}
}
