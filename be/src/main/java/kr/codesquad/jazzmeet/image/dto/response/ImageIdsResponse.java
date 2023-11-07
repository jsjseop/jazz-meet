package kr.codesquad.jazzmeet.image.dto.response;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ImageIdsResponse {

	private List<Long> ids;
}
