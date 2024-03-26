package kr.codesquad.jazzmeet.show.repository;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import kr.codesquad.jazzmeet.global.error.CustomException;
import kr.codesquad.jazzmeet.global.error.statuscode.ShowErrorCode;
import kr.codesquad.jazzmeet.show.dto.request.RegisterShowRequest;
import kr.codesquad.jazzmeet.show.util.ResponseParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class OcrHandler {

	@Value("${naver.clova.endpoint}")
	private String apiURL;
	@Value("${naver.clova.secretKey}")
	private String secretKey;

	private final ResponseParser responseParser;

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

		throw new CustomException(ShowErrorCode.OCR_FAILED);
	}

	private List<RegisterShowRequest> getShowRequests(String venueName, String scheduleUrl, List<String> posterUrls,
		LocalDate latestShowDate) {
		StringBuffer response = sendRequest(scheduleUrl);
		List<RegisterShowRequest> requests = responseParser.toRegisterShowRequest(venueName, response, posterUrls,
			latestShowDate);

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
			throw new CustomException(ShowErrorCode.OCR_FAILED);
		}
	}

}
