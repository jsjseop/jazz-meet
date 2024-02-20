package kr.codesquad.jazzmeet.global.util;

import java.util.List;

public class TextParser {

	public static int getMonth(String text) {
		// text = "12.2"
		return Integer.parseInt(text.split("\\.")[0]);
	}

	public static int getDayOfMonth(String text) {
		// text = "12.2"
		return Integer.parseInt(text.split("\\.")[1]);
	}

	public static String parseDate(String text, boolean isStartShow) {
		int index = 0;
		List<String> parsedDate = parseDate(text);
		if (!isStartShow) {
			index = 1;
		}

		return parsedDate.get(index);
	}

	private static List<String> parseDate(String text) {
		// text = "12.27 - 12.31 아티스트 라인업입니다!"
		int index = 0;
		String[] showStartEndDate = text.replace(" ", "").split("-");
		showStartEndDate[1] = showStartEndDate[1].substring(0, 5); // 날짜 뒤의 텍스트 제거

		// 시작 공연, 끝 공연
		return List.of(showStartEndDate[0], showStartEndDate[1]);
	}
}
