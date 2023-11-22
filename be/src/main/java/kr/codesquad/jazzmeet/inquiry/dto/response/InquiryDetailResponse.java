package kr.codesquad.jazzmeet.inquiry.dto.response;

import kr.codesquad.jazzmeet.inquiry.dto.response.answer.InquiryAnswerDetail;

public record InquiryDetailResponse(
	Long id,
	String content,
	InquiryAnswerDetail answer
) {
}
