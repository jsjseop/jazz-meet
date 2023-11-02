package kr.codesquad.jazzmeet.fixture;

import java.time.LocalDateTime;

import kr.codesquad.jazzmeet.show.entity.Show;
import kr.codesquad.jazzmeet.venue.entity.Venue;

public class ShowFixture {

	public static Show createShow(String teamName, LocalDateTime startTime, LocalDateTime endTime) {
		return Show.builder()
			.teamName(teamName)
			.startTime(startTime)
			.endTime(endTime)
			.build();
	}

	public static Show createShow(String teamName, LocalDateTime startTime, LocalDateTime endTime, Venue venue) {
		return Show.builder()
			.teamName(teamName)
			.startTime(startTime)
			.endTime(endTime)
			.venue(venue)
			.build();
	}
}
