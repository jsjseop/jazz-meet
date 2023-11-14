package kr.codesquad.jazzmeet.show.dto.response;

import java.time.LocalDateTime;

import kr.codesquad.jazzmeet.show.vo.ShowPoster;
import lombok.Builder;

@Builder
public record ShowDetailResponse(
	Long id,
	String teamName,
	String venueName,
	String description,
	ShowPoster poster,
	LocalDateTime startTime,
	LocalDateTime endTime
) {
}
