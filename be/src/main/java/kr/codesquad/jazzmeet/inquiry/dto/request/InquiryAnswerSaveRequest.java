package kr.codesquad.jazzmeet.inquiry.dto.request;

public record InquiryAnswerSaveRequest(
	Long inquiryId,
	String content
) {
}
