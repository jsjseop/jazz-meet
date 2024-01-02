package kr.codesquad.jazzmeet.show.repository;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import kr.codesquad.jazzmeet.global.error.CustomException;
import kr.codesquad.jazzmeet.global.error.statuscode.ShowErrorCode;
import kr.codesquad.jazzmeet.show.dto.request.RegisterShowRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class OcrHandler {

	public static final String SHOW_DATE = "Show Date";
	public static final String NAME = "name";
	public static final String TEAMS = "Teams";
	public static final String INFER_TEXT = "inferText";
	private static final String SCHEDULE = "스케줄";
	private static final LocalTime ENTRY55_START_TIME_1ST = LocalTime.of(7, 30);
	private static final LocalTime ENTRY55_START_TIME_2ND = LocalTime.of(9, 40);
	private static final LocalTime ENTRY55_START_TIME_SAT_SPECIAL = LocalTime.of(4, 20);
	private static final int ENTRY55_RUNTIME = 55;

	@Value("${naver.clova.endpoint}")
	private String apiURL;
	@Value("${naver.clova.secretKey}")
	private String secretKey;

	@Transactional
	public List<RegisterShowRequest> getShows(String venueName, String imageUrl, LocalDate latestShowDate) {
		StringBuffer response = sendRequest(imageUrl);
		List<RegisterShowRequest> requests = toRegisterShowRequest(venueName, response, latestShowDate);
		if (requests.isEmpty()) {
			throw new CustomException(ShowErrorCode.OBJECT_TRANSFORMATION_FAILD);
		}

		return requests;
	}

	private StringBuffer sendRequest(String imageUrl) {
		try {
			URL url = new URL(apiURL);
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setUseCaches(false);
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json; charset=utf-8");
			con.setRequestProperty("X-OCR-SECRET", secretKey);

			JSONObject json = new JSONObject();
			json.put("version", "V2");
			json.put("requestId", UUID.randomUUID().toString());
			json.put("timestamp", System.currentTimeMillis());
			JSONObject image = new JSONObject();
			image.put("format", "jpg");
			image.put("url", imageUrl); // image should be public, otherwise, should use data
			image.put("name", "demo");
			JSONArray images = new JSONArray();
			images.put(image);
			json.put("images", images);
			String postParams = json.toString();

			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(postParams);
			wr.flush();
			wr.close();

			int responseCode = con.getResponseCode();
			BufferedReader br;
			if (responseCode == 200) {
				br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			} else {
				br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			}
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = br.readLine()) != null) {
				response.append(inputLine);
			}
			br.close();

			log.debug("OCR Response: {}", response);
			return response;
		} catch (Exception e) {
			throw new CustomException(ShowErrorCode.OCR_REQUEST_FAILED);
		}
	}

	private List<RegisterShowRequest> toRegisterShowRequest(String venueName, StringBuffer response,
		LocalDate latestShowDate) {
		// JSON 파싱
		JSONTokener tokener = new JSONTokener(response.toString());
		JSONObject object = new JSONObject(tokener);
		JSONObject images = object.getJSONArray("images").getJSONObject(0);
		// 일치하지 않는 공연장 템플릿(OCR)이 적용되었으면 에러를 반환한다.
		String templateName = images.getJSONObject("matchedTemplate").get(NAME).toString();
		String venueNameSchedule = venueName + " " + SCHEDULE;
		if (!templateName.equals(venueNameSchedule)) {
			throw new CustomException(ShowErrorCode.OCR_NOT_MATCHED_VENUE_AND_IMAGE);
		}
		JSONArray fields = images.getJSONArray("fields");
		LocalDate firstShowDate = null;
		// 아티스트의 이름, 상세(=연주자들)
		String teamsText = null;
		// response로 받은 공연 데이터가 최신 데이터인지 확인하는 flag
		boolean isShowDataAfterLatestShow = false;
		for (int i = 0; i < fields.length(); i++) {
			JSONObject jsonObject = fields.getJSONObject(i);
			// 처음 공연 날짜 , 마지막 공연 날짜
			if (jsonObject.get(NAME).equals(SHOW_DATE)) {
				String showFirstLastDateText = jsonObject.get(INFER_TEXT).toString();
				firstShowDate = makeShowStartLocalDate(showFirstLastDateText);
				// response로 받은 공연 시작 날짜가, 공연장에 등록 된 가장 최신 공연 날짜보다 뒤라면
				if (latestShowDate == null || firstShowDate.isAfter(latestShowDate)) {
					// 최신 데이터로 flag 변경.
					isShowDataAfterLatestShow = true;
				}
			}

			// 아티스트 목록
			if (jsonObject.get(NAME).equals(TEAMS)) {
				teamsText = jsonObject.get(INFER_TEXT).toString();
			}
		}

		if (firstShowDate == null) {
			throw new CustomException(ShowErrorCode.OCR_EXTRACTION_FAILED_SHOW_DATE);
		}

		if (teamsText == null) {
			throw new CustomException(ShowErrorCode.OCR_EXTRACTION_FAILED_ARTISTS_AND_DETAILS);
		}

		if (!isShowDataAfterLatestShow) {
			throw new CustomException(ShowErrorCode.OCR_NOT_LATEST_SHOW);
		}

		// response가 최신 데이터라면 파싱해서 공연 등록 request로 만든다.
		return makeRegisterShowRequest(firstShowDate, teamsText);
	}

	private LocalDate makeShowStartLocalDate(String showStartEndDateText) {
		// inferText = "12.27 - 12.31"
		String[] showStartEndDate = showStartEndDateText.replace(" ", "").split("-");
		List<Integer> showStartMonthDay = Arrays.stream(showStartEndDate[0].split("\\."))
			.map(Integer::parseInt).toList();
		Month showStartMonth = Month.of(showStartMonthDay.get(0));
		Integer showStartDay = showStartMonthDay.get(1);
		LocalDate now = LocalDate.now();
		int showYear = now.getYear();
		// 오늘이 11월이나 12월이고, 공연 날짜가 1월이나 2월이면 공연의 연도를 내년으로 설정.
		if ((now.getMonth().equals(Month.NOVEMBER) || now.getMonth().equals(Month.DECEMBER))
			&& (showStartMonth.equals(Month.JANUARY) || showStartMonth.equals(Month.FEBRUARY))) {
			showYear += 1;
		}

		return LocalDate.of(showYear, showStartMonth, showStartDay);
	}

	private List<RegisterShowRequest> makeRegisterShowRequest(LocalDate firstShowDate, String teams) {
		List<RegisterShowRequest> requests = new ArrayList<>();
		String[] splitedArtists = teams.split("\n");
		LocalDate showDate = firstShowDate;

		for (int i = 0; i < splitedArtists.length; i += 2) {
			String teamName = splitedArtists[i];
			String teamMusician = splitedArtists[i + 1];

			if (showDate.getDayOfWeek().equals(DayOfWeek.SATURDAY)) { // 토요일만 스케줄이 다르다.
				LocalDateTime specialShowStartTime = LocalDateTime.of(showDate, ENTRY55_START_TIME_SAT_SPECIAL);
				LocalDateTime specialShowEndTime = specialShowStartTime.minusMinutes(ENTRY55_RUNTIME);
				RegisterShowRequest request = RegisterShowRequest.builder()
					.teamName(teamName)
					.description(teamMusician)
					.startTime(specialShowStartTime)
					.endTime(specialShowEndTime).build();
				requests.add(request);
				continue;
			}

			LocalDateTime firstShowStartTime = LocalDateTime.of(showDate, ENTRY55_START_TIME_1ST);
			LocalDateTime firstShowEndTime = firstShowStartTime.plusMinutes(ENTRY55_RUNTIME);
			LocalDateTime secondShowStartTime = LocalDateTime.of(showDate, ENTRY55_START_TIME_2ND);
			LocalDateTime secondShowEndTime = secondShowStartTime.plusMinutes(ENTRY55_RUNTIME);

			RegisterShowRequest firstShowRequest = RegisterShowRequest.builder()
				.teamName(teamName)
				.description(teamMusician)
				.startTime(firstShowStartTime)
				.endTime(firstShowEndTime)
				.build();
			RegisterShowRequest secondShowRequest = RegisterShowRequest.builder()
				.teamName(teamName)
				.description(teamMusician)
				.startTime(secondShowStartTime)
				.endTime(secondShowEndTime)
				.build();

			requests.add(firstShowRequest);
			requests.add(secondShowRequest);

			// 다음 날로 변경
			showDate = showDate.plusDays(1);
		}

		return requests;
	}

}
