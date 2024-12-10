package kr.codesquad.jazzmeet.review.dto.request;

public record ReviewCreateRequest(

	String nickname,
	String password,
	String content,
	Long venueId
) {
}
