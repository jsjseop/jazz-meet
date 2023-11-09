package kr.codesquad.jazzmeet.inquiry.dto.request;

public record InquirySaveRequest(
	String category,
	String nickname,
	String password,
	String content
) {
}
