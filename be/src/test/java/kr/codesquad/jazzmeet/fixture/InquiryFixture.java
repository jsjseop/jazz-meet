package kr.codesquad.jazzmeet.fixture;

import kr.codesquad.jazzmeet.inquiry.dto.request.InquiryDeleteRequest;
import kr.codesquad.jazzmeet.inquiry.dto.request.InquirySaveRequest;
import kr.codesquad.jazzmeet.inquiry.dto.request.answer.InquiryAnswerSaveRequest;
import kr.codesquad.jazzmeet.inquiry.dto.request.answer.InquiryAnswerUpdateRequest;
import kr.codesquad.jazzmeet.inquiry.entity.Answer;
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
			.status(InquiryStatus.WAITING)
			.build();
	}

	public static Inquiry createInquiry(InquiryCategory category, String nickname) {
		return Inquiry.builder()
			.nickname(nickname)
			.password("비밀번호")
			.content("내용")
			.category(category)
			.status(InquiryStatus.WAITING)
			.build();
	}

	public static Inquiry createInquiry(String content, InquiryCategory category) {
		return Inquiry.builder()
			.nickname("닉네임")
			.password("비밀번호")
			.content(content)
			.category(category)
			.status(InquiryStatus.WAITING)
			.build();
	}

	public static Inquiry createInquiry() {
		return Inquiry.builder()
			.nickname("닉네임")
			.password("비밀번호")
			.content("문의 내용")
			.category(InquiryCategory.SERVICE)
			.status(InquiryStatus.WAITING)
			.build();
	}

	public static Inquiry createInquiry(String password) {
		return Inquiry.builder()
			.nickname("닉네임")
			.password(password)
			.content("문의 내용")
			.category(InquiryCategory.SERVICE)
			.status(InquiryStatus.WAITING)
			.build();
	}

	public static Inquiry createInquiry(String password, InquiryStatus status) {
		return Inquiry.builder()
			.nickname("닉네임")
			.password(password)
			.content("문의 내용")
			.category(InquiryCategory.SERVICE)
			.status(status)
			.build();
	}

	public static Inquiry createInquiry(InquiryStatus status) {
		return Inquiry.builder()
			.nickname("닉네임")
			.password("비밀번호")
			.content("문의 내용")
			.category(InquiryCategory.SERVICE)
			.status(status)
			.build();
	}

	public static Answer createInquiryAnswer(Inquiry inquiry) {
		return Answer.builder()
			.adminId(1L)
			.content("답변 내용")
			.inquiry(inquiry)
			.build();
	}

	public static Answer createInquiryAnswer(Inquiry inquiry, String content) {
		return Answer.builder()
			.adminId(1L)
			.content(content)
			.inquiry(inquiry)
			.build();
	}

	public static InquirySaveRequest createInquirySaveRequest(String category, String nickname, String password,
		String content) {
		return InquirySaveRequest.builder()
			.category(category)
			.nickname(nickname)
			.password(password)
			.content(content)
			.build();
	}

	public static InquiryDeleteRequest createInquiryDeleteRequest(String password) {
		return new InquiryDeleteRequest(password);
	}

	public static InquiryAnswerSaveRequest createInquiryAnswerSaveRequest(Long inquiryId, String content) {
		return new InquiryAnswerSaveRequest(inquiryId, content);
	}

	public static InquiryAnswerUpdateRequest createInquiryAnswerUpdateRequest(String content) {
		return new InquiryAnswerUpdateRequest(content);
	}

	public static InquiryAnswerSaveRequest createInquiryAnswerSaveRequest(Long inquiryId) {
		return new InquiryAnswerSaveRequest(inquiryId, "답변 내용");
	}
}
