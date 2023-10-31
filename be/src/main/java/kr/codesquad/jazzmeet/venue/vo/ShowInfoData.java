package kr.codesquad.jazzmeet.venue.vo;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ShowInfoData {
	private LocalDateTime startTime;
	private LocalDateTime endTime;
}
