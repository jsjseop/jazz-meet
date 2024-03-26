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
import java.util.ArrayList;
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
import kr.codesquad.jazzmeet.global.util.CustomLocalDate;
import kr.codesquad.jazzmeet.image.service.ImageService;
import kr.codesquad.jazzmeet.show.dto.request.RegisterShowRequest;
import kr.codesquad.jazzmeet.show.mapper.ShowMapper;
import kr.codesquad.jazzmeet.show.util.TextParser;
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
	private static final int ENTRY55_RUNTIME = 55;
	private static final String NOT_FOUND_MATCHED_TEMPLATE = "not found matched template";

	@Value("${naver.clova.endpoint}")
	private String apiURL;
	@Value("${naver.clova.secretKey}")
	private String secretKey;
	private final ImageService imageService;

	@Transactional
	public List<RegisterShowRequest> getShows(String venueName, String scheduleUrl, List<String> posterUrls,
		LocalDate latestShowDate) {
		try {
			return getShowRequests(venueName, scheduleUrl, posterUrls, latestShowDate);

		} catch (CustomException e) {
			if (e.getStatusCode().equals(ShowErrorCode.OCR_NOT_FOUND_MATCHED_TEMPLATE)) {
				// 크롤링 했던 다음 이미지로 공연 스케줄 OCR 요청 재시도
				String newScheduleUrl = posterUrls.get(0);
				posterUrls.remove(0);

				return getShowRequests(venueName, newScheduleUrl, posterUrls, latestShowDate);
			}
		}

		throw new CustomException(ShowErrorCode.OCR_REQUEST_FAILED);
	}

	private List<RegisterShowRequest> getShowRequests(String venueName, String scheduleUrl, List<String> posterUrls,
		LocalDate latestShowDate) {
		StringBuffer response = sendRequest(scheduleUrl);
		List<RegisterShowRequest> requests = toRegisterShowRequest(venueName, response, posterUrls, latestShowDate);

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
		List<String> posterUrls, LocalDate latestShowDate) {
		// JSON 파싱
		JSONTokener tokener = new JSONTokener(response.toString());
		JSONObject object = new JSONObject(tokener);
		JSONObject images = object.getJSONArray("images").getJSONObject(0);
		// 공연장 템플릿(OCR)이 적용되지 않았으면 에러를 반환하고, 크롤링으로 가져왔던 다음 이미지로 OCR 요청을 다시 시도한다.
		String responseMessage = images.get("message").toString();
		if (responseMessage.contains(NOT_FOUND_MATCHED_TEMPLATE)) {
			throw new CustomException(ShowErrorCode.OCR_NOT_FOUND_MATCHED_TEMPLATE);
		}
		// 일치하지 않는 공연장 템플릿(OCR)이 적용되었으면 에러를 반환한다.
		String templateName = images.getJSONObject("matchedTemplate").get(NAME).toString();
		String venueNameSchedule = venueName + " " + SCHEDULE;
		if (!templateName.equals(venueNameSchedule)) {
			throw new CustomException(ShowErrorCode.OCR_NOT_MATCHED_VENUE_AND_IMAGE);
		}

		JSONArray fields = images.getJSONArray("fields");
		LocalDate firstShowDate = null;
		// 공연 스케줄 - 그 주의 시작 공연 날짜, 끝 공연 날짜
		String showFirstLastDateText = null;
		// 아티스트의 이름, 상세(=연주자들)
		String teamsText = null;
		// response로 받은 공연 데이터가 최신 데이터인지 확인하는 flag
		boolean isShowDataAfterLatestShow = false;
		for (int i = 0; i < fields.length(); i++) {
			JSONObject jsonObject = fields.getJSONObject(i);
			// 처음 공연 날짜 , 마지막 공연 날짜
			if (jsonObject.get(NAME).equals(SHOW_DATE)) {
				showFirstLastDateText = jsonObject.get(INFER_TEXT).toString();
			}

			// 아티스트 목록
			if (jsonObject.get(NAME).equals(TEAMS)) {
				teamsText = jsonObject.get(INFER_TEXT).toString();
			}
		}

		// 공연 날짜 필드에 값이 없으면, "팀 정보" 필드로 인식 되었을 수 있다.
		if (showFirstLastDateText == null || showFirstLastDateText.isBlank()) {
			// 팀 정보에서 개행 문자 기준, 첫 줄을 공연 날짜에 넣어준다.
			showFirstLastDateText = teamsText.split("\n")[0];
			// 공연 날짜에 넣어준 줄(newLine)을 제외한 나머지 텍스트를 팀 정보에 적용한다.
			int newLineIndex = teamsText.indexOf("\n");
			teamsText = teamsText.substring(newLineIndex + 1);
			// 텍스트에 공연 날짜가 섞여있을 수 있기 때문에 공연 날짜만 추출한다.
			showFirstLastDateText = TextParser.findDate(showFirstLastDateText);
		}

		firstShowDate = makeShowStartLocalDate(showFirstLastDateText);
		// response로 받은 공연 시작 날짜가, 공연장에 등록 된 가장 최신 공연 날짜보다 뒤라면
		if (latestShowDate == null || firstShowDate.isAfter(latestShowDate)) {
			// 최신 데이터로 flag 변경.
			isShowDataAfterLatestShow = true;
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
		return makeRegisterShowRequest(firstShowDate, teamsText, posterUrls);
	}

	private LocalDate makeShowStartLocalDate(String showStartEndDateText) {
		boolean isStartShow = true; // 시작 공연 파싱
		String showDateText = TextParser.parseDate(showStartEndDateText, isStartShow);

		return CustomLocalDate.of(showDateText);
	}

	private List<RegisterShowRequest> makeRegisterShowRequest(LocalDate firstShowDate, String teams,
		List<String> posterUrls) {
		List<RegisterShowRequest> requests = new ArrayList<>();
		String[] splitedArtists = teams.split("\n");
		LocalDate showDate = firstShowDate;
		boolean isSatFirstShow = false;

		if ((splitedArtists.length / 2) != posterUrls.size()) { // 팀(+설명)과 포스터의 수가 맞지 않으면 예외 발생
			throw new CustomException(ShowErrorCode.OCR_NOT_EQUAL_TEAMS_AND_POSTER_NUMBERS);
		}

		List<Long> posterIds = imageService.uploadPosters(posterUrls);

		for (int i = 0; i < splitedArtists.length; i += 2) {
			String teamName = splitedArtists[i];
			String teamMusician = splitedArtists[i + 1];
			// 포스터 index는 순차적으로 올라간다.
			Long posterId = posterIds.get(i / 2);

			if (isSatFirstShow) { // 토요일만 스케줄이 다르다.
				// 토요일 첫번째 공연(Trio) 시간
				LocalDateTime saturdayShowStartTime = LocalDateTime.of(showDate, LocalTime.of(15, 50));
				LocalDateTime saturdayShowEndTime = saturdayShowStartTime.plusMinutes(ENTRY55_RUNTIME);
				RegisterShowRequest request = ShowMapper.INSTANCE.toRegisterShowRequest(teamName, teamMusician,
					posterId, saturdayShowStartTime, saturdayShowEndTime);

				requests.add(request);
				// 첫번째 공연 플래그 변경
				isSatFirstShow = false;
				continue;
			}

			// 공연 시작 시간 설정
			LocalTime firstShowStartTime = setFirstShowTime(showDate.getDayOfWeek());
			LocalTime secondShowStartTime = setSecondShowTime(showDate.getDayOfWeek());

			// 공연 날짜 + 시간(시작, 끝) 설정
			LocalDateTime firstShowStartDateTime = LocalDateTime.of(showDate, firstShowStartTime);
			LocalDateTime firstShowEndTime = firstShowStartDateTime.plusMinutes(ENTRY55_RUNTIME);
			LocalDateTime secondShowStartDateTime = LocalDateTime.of(showDate, secondShowStartTime);
			LocalDateTime secondShowEndTime = secondShowStartDateTime.plusMinutes(ENTRY55_RUNTIME);

			RegisterShowRequest firstShowRequest = ShowMapper.INSTANCE.toRegisterShowRequest(teamName, teamMusician,
				posterId, firstShowStartDateTime, firstShowEndTime);
			RegisterShowRequest secondShowRequest = ShowMapper.INSTANCE.toRegisterShowRequest(teamName, teamMusician,
				posterId, secondShowStartDateTime, secondShowEndTime);

			requests.add(firstShowRequest);
			requests.add(secondShowRequest);

			// 다음 날로 변경
			showDate = showDate.plusDays(1);

			// 토요일 공연이면 첫번째 공연 플래그를 변경해준다.
			if (showDate.getDayOfWeek().equals(DayOfWeek.SATURDAY)) {
				isSatFirstShow = true;
			}
		}

		return requests;
	}

	private LocalTime setFirstShowTime(DayOfWeek dayOfWeek) {
		// 토요일 두번째(Special GIG - 1) 공연 시작 시간
		if (dayOfWeek.equals(DayOfWeek.SATURDAY)) {
			return LocalTime.of(18, 45);
		}
		// 일요일 첫번째 공연 시작 시간
		if (dayOfWeek.equals(DayOfWeek.SUNDAY)) {
			return LocalTime.of(18, 30);
		}

		// 평일 첫번째 공연 시작 시간 (월~금)
		return LocalTime.of(19, 30);
	}

	private LocalTime setSecondShowTime(DayOfWeek dayOfWeek) {
		// 토요일 세번째(Special GIG - 2) 공연 시작 시간
		if (dayOfWeek.equals(DayOfWeek.SATURDAY)) {
			return LocalTime.of(21, 10);
		}
		// 일요일 두번째 공연 시작 시간
		if (dayOfWeek.equals(DayOfWeek.SUNDAY)) {
			return LocalTime.of(20, 40);
		}

		// 평일 두번째 공연 시작 시간 (월~금)
		return LocalTime.of(21, 40);
	}

}
