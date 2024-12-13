package kr.codesquad.jazzmeet.review.vo;

public record ReviewDetail(
	Long id,
	String content,
	String userId,
	int likeCount,
	int dislikeCount
) {
}
