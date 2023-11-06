package kr.codesquad.jazzmeet.inquiry.dto.response;

import java.util.List;

import kr.codesquad.jazzmeet.inquiry.vo.InquirySearch;

public record InquirySearchResponse(
	List<InquirySearch> inquiries,
	long totalCount,
	int currentPage,
	int maxPage
) {
}
