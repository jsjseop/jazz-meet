package kr.codesquad.jazzmeet.global.util;

import java.time.Month;
import java.util.Arrays;
import java.util.List;

public class TextParser {

	public static Month getMonth(String text) {
		return Month.of(parse(text).get(0));
	}

	public static Integer getDayOfMonth(String text) {
		return parse(text).get(1);
	}

	private static List<Integer> parse(String text) {
		// text = "12.27 - 12.31"
		String[] showStartEndDate = text.replace(" ", "").split("-");

		return Arrays.stream(showStartEndDate[0].split("\\."))
			.map(Integer::parseInt).toList();
	}
}
