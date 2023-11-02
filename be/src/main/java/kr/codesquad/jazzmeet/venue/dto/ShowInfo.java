package kr.codesquad.jazzmeet.venue.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ShowInfo {
	private LocalDateTime startTime;
	private LocalDateTime endTime;

	public boolean emptyCheck() {
		return startTime == null && endTime == null;
	}
}
