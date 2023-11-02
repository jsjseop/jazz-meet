package kr.codesquad.jazzmeet.fixture;

import java.time.LocalDateTime;

import kr.codesquad.jazzmeet.show.entity.Show;

public class ShowFixture {
	public static Show createShow(String name, LocalDateTime startTime, LocalDateTime endTime) {
		return Show.builder().teamName(name).startTime(startTime).endTime(endTime).build();
	}
}
