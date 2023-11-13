package kr.codesquad.jazzmeet.show.dto.response;

import java.util.List;

import kr.codesquad.jazzmeet.show.vo.ShowWithVenue;

public record ShowByDateResponse(
	String region,
	List<ShowWithVenue> venues
) {
}
