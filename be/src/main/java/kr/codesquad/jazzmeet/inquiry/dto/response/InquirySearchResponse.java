package kr.codesquad.jazzmeet.inquiry.dto.response;

import java.util.List;

public record InquirySearchResponse(
	List<InquirySearch> inquiries,
	long totalCount,
	int currentPage,
	int maxPage
) {
}
