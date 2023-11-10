package kr.codesquad.jazzmeet.fixture;

import java.time.LocalDateTime;

import kr.codesquad.jazzmeet.image.entity.Image;
import kr.codesquad.jazzmeet.image.entity.ImageStatus;
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

	public static Show createShow(String teamName, LocalDateTime time, Venue venue) {
		return Show.builder()
			.teamName(teamName)
			.poster(new Image("image.url", ImageStatus.REGISTERED, LocalDateTime.now()))
			.startTime(time)
			.endTime(time)
			.venue(venue)
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
