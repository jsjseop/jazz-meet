package kr.codesquad.jazzmeet.venue.dto;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class ShowInfo {

	private LocalDateTime startTime;
	private LocalDateTime endTime;
}
