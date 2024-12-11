package kr.codesquad.jazzmeet.review.dto.request;

public record ReviewCreateRequest(

	String content,
	Long venueId
) {
}
