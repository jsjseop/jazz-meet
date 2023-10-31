package kr.codesquad.jazzmeet.venue.entity;

import java.time.DateTimeException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum DayOfWeek {
	MONDAY("월요일"),
	TUESDAY("화요일"),
	WEDNESDAY("수요일"),
	THURSDAY("목요일"),
	FRIDAY("금요일"),
	SATURDAY("토요일"),
	SUNDAY("일요일");

	private final String name;

	private static final DayOfWeek[] ENUMS = DayOfWeek.values();

	public static String getName(int index) {
		if (index < 0 || index > 6) {
			throw new DateTimeException("Invalid value for DayOfWeek: " + index);
		}
		return ENUMS[index].name;
	}
}
