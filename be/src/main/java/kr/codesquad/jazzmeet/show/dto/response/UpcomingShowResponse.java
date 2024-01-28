package kr.codesquad.jazzmeet.show.dto.response;

import java.time.LocalDateTime;

public record UpcomingShowResponse(
	Long venueId,
	String venueName,
	Long showId,
	String posterUrl,
	String teamName,
	LocalDateTime startTime,
	LocalDateTime endTime) {
}
