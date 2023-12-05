package kr.codesquad.jazzmeet.image.dto.response;

import java.util.List;

public record ImageCreateResponse(
	List<ImageSaveResponse> images
) {
}
