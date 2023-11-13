package kr.codesquad.jazzmeet.show.vo;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ShowSummary {
	private Long id;
	private String posterUrl;
	private String teamName;
	private LocalDateTime startTime;
	private LocalDateTime endTime;

	public ShowSummary(Long id, String posterUrl, String teamName, LocalDateTime startTime,
		LocalDateTime endTime) {
		this.id = id;
		this.posterUrl = posterUrl;
		this.teamName = teamName;
		this.startTime = startTime;
		this.endTime = endTime;
	}
}
