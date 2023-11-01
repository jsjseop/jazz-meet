package kr.codesquad.jazzmeet.venue.dto;

import java.time.LocalDateTime;

public record ShowInfo(
	LocalDateTime startTime,
	LocalDateTime endTime) {
}
