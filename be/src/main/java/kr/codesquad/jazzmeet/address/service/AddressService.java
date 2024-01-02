package kr.codesquad.jazzmeet.address.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import kr.codesquad.jazzmeet.address.dto.response.AddressResponse;
import kr.codesquad.jazzmeet.address.dto.response.AddressSearchResponse;
import kr.codesquad.jazzmeet.global.error.CustomException;
import kr.codesquad.jazzmeet.global.error.statuscode.AddressErrorCode;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class AddressService {

	@Value("${address.endpoint}")
	private String endpoint;
	@Value("${address.client.id}")
	private String clientId;
	@Value("${address.client.secret}")
	private String clientSecret;

	public AddressSearchResponse search(String word, int page) {
		if (word == null || word == "") {
			throw new CustomException(AddressErrorCode.WORD_IS_EMPTY);
		}
		// 네이버에 요청을 보낸다.
		StringBuffer response = sendRequest(word, page);
		// 받은 response를 원하는 response 형태로 만들기.
		return toLocationSearchResponse(response);
	}

	private StringBuffer sendRequest(String word, int page) {
		HttpURLConnection conn = null;

		try {
			String address = URLEncoder.encode(word, StandardCharsets.UTF_8);
			String pageNum = URLEncoder.encode(String.valueOf(page), StandardCharsets.UTF_8);
			String apiUrl = endpoint + "?query=" + address + "&page=" + pageNum;

			URL url = new URL(apiUrl);
			conn = (HttpURLConnection)url.openConnection();
			// 요청 메서드
			conn.setRequestMethod("GET");
			// 요청 헤더
			conn.setRequestProperty("X-NCP-APIGW-API-KEY-ID", clientId);
			conn.setRequestProperty("X-NCP-APIGW-API-KEY", clientSecret);
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Accept", "application/json");

			// 요청 결과 확인. 정상 호출인 경우 200
			int responseCode = conn.getResponseCode();
			log.debug("Response Code: " + responseCode);

			// 응답 데이터 읽기
			BufferedReader br;

			if (responseCode == 200) {
				br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
			} else {
				br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
			}

			StringBuffer response = new StringBuffer();
			String responseLine;
			while ((responseLine = br.readLine()) != null) {
				response.append(responseLine.trim());
			}
			log.debug("Response Data: " + response.toString());

			br.close();

			return response;

		} catch (Exception e) {
			throw new CustomException(AddressErrorCode.REQUEST_FAILED);
		} finally {
			// 에러와 관계없이 모든 로직이 끝나면 모든 연결을 종료.
			if (conn != null) {
				conn.disconnect();
			}
		}
	}

	private AddressSearchResponse toLocationSearchResponse(StringBuffer response) {
		// JSON 파싱
		JSONTokener tokener = new JSONTokener(response.toString());
		JSONObject object = new JSONObject(tokener);
		JSONObject meta = object.getJSONObject("meta");

		meta.get("totalCount");
		long totalCount = Long.parseLong(String.valueOf(meta.get("totalCount")));
		int count = Integer.parseInt(String.valueOf(meta.get("count")));
		int currentPage = 0;
		int maxPage = 0;
		if (totalCount != 0 && count != 0) {
			currentPage = Integer.parseInt(String.valueOf(meta.get("page")));
			maxPage = (int)(totalCount / count);
			if (totalCount % count != 0) {
				maxPage += 1;
			}
		}
		List<AddressResponse> addressses = toAddressSearchResponse(object);

		return AddressSearchResponse.builder()
			.addresses(addressses)
			.totalCount(totalCount)
			.currentPage(currentPage)
			.maxPage(maxPage)
			.build();
	}

	private List<AddressResponse> toAddressSearchResponse(JSONObject object) {
		List<AddressResponse> addresses = new ArrayList<>();
		JSONArray arr = object.getJSONArray("addresses");

		for (int i = 0; i < arr.length(); i++) {
			JSONObject temp = (JSONObject)arr.get(i);
			String roadNameAddress = (String)temp.get("roadAddress");
			String lotNumberAddress = (String)temp.get("jibunAddress");
			Double latitude = Double.parseDouble((String)temp.get("y"));
			Double longitude = Double.parseDouble((String)temp.get("x"));

			AddressResponse address = AddressResponse.builder()
				.roadNameAddress(roadNameAddress)
				.lotNumberAddress(lotNumberAddress)
				.latitude(latitude)
				.longitude(longitude)
				.build();

			addresses.add(address);
		}

		return addresses;
	}
}
