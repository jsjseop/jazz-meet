package kr.codesquad.jazzmeet.venue.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ShowInfo {
	private LocalDateTime startTime;
	private LocalDateTime endTime;

	public boolean emptyCheck() {
		return startTime == null && endTime == null;
	}
}
