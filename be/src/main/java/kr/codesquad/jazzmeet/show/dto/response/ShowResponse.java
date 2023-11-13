package kr.codesquad.jazzmeet.show.dto.response;

import java.util.List;

import kr.codesquad.jazzmeet.show.vo.ShowSummaryWithVenue;
import lombok.Builder;

@Builder
public record ShowResponse(
	List<ShowSummaryWithVenue> shows,
	long totalCount,
	int currentPage,
	int maxPage
) {
}
