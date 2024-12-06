package kr.codesquad.jazzmeet.venue.dto.request;

public record ReviewCreateRequest(

	String nickname,
	String password,
	String content,
	Long venueId
) {
}
