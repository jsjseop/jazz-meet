package kr.codesquad.jazzmeet.show.vo;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ShowSummaryWithVenue {
	private Long id;
	private String teamName;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private String venueName;

	public ShowSummaryWithVenue(Long id, String teamName, LocalDateTime startTime, LocalDateTime endTime,
		String venueName) {
		this.id = id;
		this.teamName = teamName;
		this.startTime = startTime;
		this.endTime = endTime;
		this.venueName = venueName;
	}
}
