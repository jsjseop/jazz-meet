package kr.codesquad.jazzmeet.show.repository;

import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import kr.codesquad.jazzmeet.global.error.CustomException;
import kr.codesquad.jazzmeet.global.error.statuscode.ShowErrorCode;
import kr.codesquad.jazzmeet.global.util.CustomLocalDate;
import kr.codesquad.jazzmeet.global.util.TextParser;
import kr.codesquad.jazzmeet.global.util.WebDriverUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@Component
public class WebCrawler {

	private static final String NEXT_ARTICLE_BUTTON_CLASS_NAME = "_abl-";
	public static final String NEXT_PHOTO_BUTTON_CLASS_NAME = "_9zm2";
	public static final int MAX_FIXED_ARTICLE_NUMBER = 3;
	public static final int DEFAULT_SHOW_DAY_COUNT = 5;

	@Value("${instagram.id}")
	private String instagramId;
	@Value("${instagram.password}")
	private String instagramPassword;

	public HashMap<String, List<String>> getShowImageUrls(String venueInstagramUrl, LocalDate latestShowDate) {
		// key: 공연 스케줄 이미지 url
		// value: 공연 포스터 이미지 urls
		HashMap<String, List<String>> showImageUrls = null;
		WebDriver driver = WebDriverUtil.getChromeDriver();

		try {
			showImageUrls = getImageUrl(driver, venueInstagramUrl, latestShowDate);
		} catch (InterruptedException e) {
			throw new CustomException(ShowErrorCode.CRAWLING_REQUEST_FAILED);
		} finally {
			//드라이버 연결 종료
			driver.close(); //드라이버 연결 해제
			//프로세스 종료
			driver.quit();
		}

		if (showImageUrls.isEmpty()) {
			throw new CustomException(ShowErrorCode.NOT_FOUND_SHOW_IMAGE_URL);
		}

		return showImageUrls;
	}

	private HashMap<String, List<String>> getImageUrl(WebDriver driver,
		String venueInstagramUrl, LocalDate latestShowDate) throws InterruptedException {
		// 대기 시간 지정하기
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		HashMap<String, List<String>> imageUrls = new HashMap<>();

		if (!ObjectUtils.isEmpty(driver)) {
			// url로 이동
			driver.get("https://www.instagram.com/");
			// 이동 시 생기는 브라우저 로드 시간을 기다린다.
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

			// 로그인
			driver.findElements(
					By.cssSelector("#loginForm > div > div:nth-child(1) > div > label > input"))
				.get(0).sendKeys(instagramId);
			driver.findElements(
					By.cssSelector("#loginForm > div > div:nth-child(2) > div > label > input"))
				.get(0).sendKeys(instagramPassword);
			driver.findElements(
					By.cssSelector("#loginForm > div > div:nth-child(3) > button > div"))
				.get(0).click();
			// driver.manage()
			// 	.timeouts()
			// 	.implicitlyWait(Duration.ofSeconds(10)); // 암묵적 대기. 지정한 시간보다 일찍 로드되면 바로 다음 작업으로 넘어간다.
			Thread.sleep(5000); // 로그인 대기 시간

			// wait.until(ExpectedConditions.urlToBe(
			// 	"https://www.instagram.com/accounts/onetap/?next=%2F")); // 로그인 완료 후 url 전환하기까지 대기
			driver.get(venueInstagramUrl); // 공연장 instagram url로 이동
			Thread.sleep(2000); // url 이동 대기 시간 (게시글)
			
			driver.findElements(
					By.className("_aagw")) // 게시물 선택
				.get(0).click();

			Thread.sleep(2000); // 대기 시간

			for (int i = 0; i < MAX_FIXED_ARTICLE_NUMBER; i++) { // 맨 위에 고정된 게시물 3개
				int nextBtnIndex = 1;
				if (i == 0) {
					nextBtnIndex = 0;
				}
				// 게시물 설명 텍스트
				String articleText = driver.findElements(
						By.className("_a9zs"))
					.get(0).getText();
				if (!articleText.contains("라인업")) { // 스케줄 키워드가 포함되어있지 않으면 다음 게시물을 탐색한다.
					driver.findElements(By.className(NEXT_ARTICLE_BUTTON_CLASS_NAME))
						.get(nextBtnIndex)
						.click(); // 오른쪽 화살표 클릭
					continue;
				}
				// DB에 저장되어있던 가장 최근 공연과 비교해, 크롤링 한 공연 날짜가 새로운 공연이 아니면 다음 게시물을 탐색한다.
				if (!isNewShowDate(articleText, latestShowDate)) {
					driver.findElements(By.className(NEXT_ARTICLE_BUTTON_CLASS_NAME))
						.get(nextBtnIndex)
						.click();
					continue;
				}

				// 오른쪽 화살표로 img 탐색.
				// 이벤트가 존재하는 주(week)는, 공연 날짜가 추가되어 2번째 img에 스케줄표가 위치해 있다.
				driver.findElements(By.className(NEXT_PHOTO_BUTTON_CLASS_NAME))
					.get(0)
					.click();
				// 이벤트가 없는 기본(default) 주(week)는, 3번째 img에 스케줄표가 위치해 있다.
				// 공연 일(day)수가 기본(default) 일수와 동일한지 확인.
				boolean isEqualsToDefaultShowDayCount = isEqualsToDefaultShowDayCount(articleText);
				if (isEqualsToDefaultShowDayCount) {
					driver.findElements(By.className(NEXT_PHOTO_BUTTON_CLASS_NAME))
						.get(0)
						.click();
				}
				Thread.sleep(2000); // 대기 시간

				// entry55(default Schedule)인 경우 3번째 img가 주간 공연 스케줄, 4번째부터 공연 poster가 나열된다.
				WebElement img = driver.findElements(
					By.cssSelector("div._aagu._aato > div > img")).get(1); // 이미지 선택
				// 포스터 이미지
				// div._aagu _aa20 _aato > div._aagv > img .get(0)
				// 클릭해서 이동 1, 2
				String imageUrl = img.getAttribute("src");
				log.debug("Instagram Image URL: {}", imageUrl);
				if (!isEqualsToDefaultShowDayCount) {
					// 오른쪽 화살표를 클릭해 다음 이미지로 넘어간다.
					driver.findElements(By.className(NEXT_PHOTO_BUTTON_CLASS_NAME))
						.get(0)
						.click();
				}

				List<String> posterImgUrls = new ArrayList<>();
				while (true) {
					boolean existNextPhotoButton = driver.findElements(
							By.className(NEXT_ARTICLE_BUTTON_CLASS_NAME))
						.isEmpty();
					List<WebElement> elements = driver.findElements(
						By.cssSelector("div._aagu._aato > div._aagv > img"));// 이미지 선택
					int elementLastIndex = 0;
					if (!elements.isEmpty()) {
						elementLastIndex = elements.size() - 1;
					}
					String currentPosterImg = elements.get(elementLastIndex).getAttribute("src");

					if (!posterImgUrls.isEmpty()) {
						String lastPosterImg = posterImgUrls.get(posterImgUrls.size() - 1);
						// 맨 마지막 사진에 도달해 오른쪽 버튼이 존재하지 않거나,
						// 현재 읽은 img와 마지막으로 저장된 이미지 url이 동일하면 루프를 빠져나온다.
						if (existNextPhotoButton || currentPosterImg.equals(lastPosterImg)) {
							break;
						}
					}
					posterImgUrls.add(currentPosterImg);
					// 오른쪽 화살표를 클릭해 다음 이미지로 넘어간다.
					driver.findElements(By.className(NEXT_PHOTO_BUTTON_CLASS_NAME))
						.get(0)
						.click();
				}

				imageUrls.put(imageUrl, posterImgUrls);

				driver.findElements(By.className(NEXT_ARTICLE_BUTTON_CLASS_NAME))
					.get(nextBtnIndex)
					.click();
			}
		}

		return imageUrls;
	}

	private boolean isEqualsToDefaultShowDayCount(String articleText) {
		// articleText = "02.07 - 02.12 아티스트 라인업입니다!"
		String startDateText = TextParser.parseDate(articleText, true);
		LocalDate startDate = CustomLocalDate.of(startDateText);
		String endDateText = TextParser.parseDate(articleText, false);
		LocalDate endDate = CustomLocalDate.of(endDateText);
		long daysDiff = ChronoUnit.DAYS.between(startDate, endDate) + 1;

		return DEFAULT_SHOW_DAY_COUNT == daysDiff;
	}

	private boolean isNewShowDate(String articleText, LocalDate latestShowDate) {
		if (latestShowDate == null) { // 저장된 공연이 없으므로 크롤링한 공연은 무조건 최신이다.
			return true;
		}

		boolean isStartShow = true; // 시작 공연 파싱
		String showDateText = TextParser.parseDate(articleText, isStartShow);
		LocalDate showDate = CustomLocalDate.of(showDateText);

		return showDate.isAfter(latestShowDate);
	}
}
