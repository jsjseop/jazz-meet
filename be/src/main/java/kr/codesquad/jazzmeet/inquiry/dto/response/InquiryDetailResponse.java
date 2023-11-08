package kr.codesquad.jazzmeet.inquiry.dto.response;

public record InquiryDetailResponse(
	Long id,
	String content,
	InquiryAnswerDetail answer
) {
}
