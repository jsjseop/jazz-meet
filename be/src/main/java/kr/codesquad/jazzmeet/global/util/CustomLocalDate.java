package kr.codesquad.jazzmeet.global.util;

import java.time.LocalDate;
import java.time.Month;

public class CustomLocalDate {

	public static LocalDate of(String text) {
		// text = "12.2"
		Month month = Month.of(TextParser.getMonth(text));
		int dayOfMonth = TextParser.getDayOfMonth(text);

		return CustomLocalDate.of(month, dayOfMonth);
	}

	// 파싱해 온 공연 날짜 생성
	public static LocalDate of(Month month, Integer dayOfMonth) {
		LocalDate now = LocalDate.now();
		int showYear = now.getYear();
		// 오늘이 11월이나 12월이고, 공연 날짜가 1월이나 2월이면 공연의 연도를 내년으로 설정.
		if ((now.getMonth().equals(Month.NOVEMBER) || now.getMonth().equals(Month.DECEMBER))
			&& (month.equals(Month.JANUARY) || month.equals(Month.FEBRUARY))) {
			showYear += 1;
		}

		// 오늘이 1월이고, 공연 날짜가 12월이면 공연의 연도를 작년으로 설정.
		if (now.getMonth().equals(Month.JANUARY) && month.equals(Month.DECEMBER)) {
			showYear -= 1;
		}

		return LocalDate.of(showYear, month, dayOfMonth);
	}
}
