package kr.codesquad.jazzmeet.show.util;

import java.util.List;

import kr.codesquad.jazzmeet.global.error.CustomException;
import kr.codesquad.jazzmeet.global.error.statuscode.ShowErrorCode;

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

	public static String findDateLine(String text) {
		String[] splitedText = text.replace(" ", "").split("\n");
		// text = "안녕하세요 \n \n 12.27 - 12.31 아티스트 라인업입니다!"
		for (String textLine : splitedText) {
			if (textLine.isBlank()) {
				continue;
			}
			// 첫 글자에서 숫자(=날짜)가 인식되어야 한다.
			if (Character.isDigit(textLine.charAt(0))) {
				return textLine;
			}
		}

		throw new CustomException(ShowErrorCode.NOT_FOUND_SHOW_DATE);
	}

	public static String findDate(String text) {
		String temp = text.replace(" ", "");
		for (int i = 0; i < temp.length(); i++) {
			char c = temp.charAt(i);
			// 날짜(=문자)를 찾았다면 "시작 공연 날짜" 부터 추출하여 반환
			if (Character.isDigit(c)) {
				return temp.substring(i);
			}
		}

		throw new CustomException(ShowErrorCode.OCR_NOT_FOUND_DATE);
	}
}
