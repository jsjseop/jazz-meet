package kr.codesquad.jazzmeet.image.dto.response;

import java.util.List;

public record ImageIdsResponse (
	List<Long> ids
) {
}
