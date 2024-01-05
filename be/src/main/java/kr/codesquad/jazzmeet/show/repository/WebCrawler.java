package kr.codesquad.jazzmeet.show.repository;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
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
import kr.codesquad.jazzmeet.global.util.WebDriverUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@Component
public class WebCrawler {

	private static final String NEXT_ARTICLE_BUTTON_CLASS_NAME = "_abl-";
	public static final String NEXT_PHOTO_BUTTON_CLASS_NAME = "_9zm2";
	public static final int MAX_FIXED_ARTICLE_NUMBER = 2;

	@Value("${instagram.id}")
	private String instagramId;
	@Value("${instagram.password}")
	private String instagramPassword;

	public List<String> getShowImageUrls(String venueInstagramUrl, LocalDate latestShowDate) {
		List<String> showImageUrls = null;
		WebDriver driver = WebDriverUtil.getChromeDriver();

		try {
			showImageUrls = getImageUrl(driver, venueInstagramUrl, latestShowDate);
		} catch (InterruptedException e) {
			throw new CustomException(ShowErrorCode.CRAWLING_REQUEST_FAILED);
		}

		if (showImageUrls.isEmpty()) {
			throw new CustomException(ShowErrorCode.NOT_FOUND_SHOW_IMAGE_URL);
		}

		//드라이버 연결 종료
		driver.close(); //드라이버 연결 해제
		//프로세스 종료
		driver.quit();

		return showImageUrls;
	}

	private List<String> getImageUrl(WebDriver driver, String venueInstagramUrl, LocalDate latestShowDate) throws
		InterruptedException {
		// 대기 시간 지정하기
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		List<String> imageUrls = new ArrayList<>();

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
			driver.manage()
				.timeouts()
				.implicitlyWait(Duration.ofSeconds(5)); // 암묵적 대기. 지정한 시간보다 일찍 로드되면 바로 다음 작업으로 넘어간다.

			wait.until(ExpectedConditions.urlToBe(
				"https://www.instagram.com/accounts/onetap/?next=%2F")); // 로그인 완료 후 url 전환하기까지 대기
			driver.get(venueInstagramUrl); // 공연장 instagram url로 이동

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
				// TODO: 1월 둘째주부터 변경 2번째 -> 3번째로 변경.
				// entry55인 경우 3번째 img가 주간 공연 스케줄, 4번째부터 공연 poster가 나열된다.
				driver.findElements(By.className(NEXT_PHOTO_BUTTON_CLASS_NAME))
					.get(0)
					.click();
				// driver.findElements(By.className(NEXT_PHOTO_BUTTON_CLASS_NAME))
				// 	.get(0)
				// 	.click();
				Thread.sleep(2000); // 대기 시간

				// 1,2,3번 이미지는 "_aagu _aato"(3번은 스케줄), 4,5번 이미지는 "_aagu _aa20 _aato", 6,7번 이미지는 ...
				WebElement img = driver.findElements(
					By.cssSelector("div._aagu._aato > div > img")).get(1); // 이미지 선택
				String imageUrl = img.getAttribute("src");
				log.debug("Instagram Image URL: {}", imageUrl);
				// TODO: 공연 포스터 크롤링하기
				imageUrls.add(imageUrl);

				driver.findElements(By.className(NEXT_ARTICLE_BUTTON_CLASS_NAME))
					.get(nextBtnIndex)
					.click();
			}
		}

		return imageUrls;
	}

	private boolean isNewShowDate(String articleText, LocalDate latestShowDate) {
		if (latestShowDate == null) { // 저장된 공연이 없으므로 크롤링한 공연은 무조건 최신이다.
			return true;
		}

		LocalDate now = LocalDate.now();
		// articleText.split(" ")[0] = "12.28"
		List<Integer> showStartMonthDay = Arrays.stream(articleText.split(" ")[0].split("."))
			.map(Integer::parseInt).toList();
		// 추출해 온 공연 날짜를 LocalDate 형식으로 만들기
		Month showStartMonth = Month.of(showStartMonthDay.get(0));
		Integer showStartDay = showStartMonthDay.get(1);
		int showYear = now.getYear();

		// 오늘이 11월이나 12월이고, (추출해 온) 공연 날짜가 1월이나 2월이면 공연의 연도를 내년으로 설정.
		if ((now.getMonth().equals(Month.NOVEMBER) || now.getMonth().equals(Month.DECEMBER))
			&& (showStartMonth.equals(Month.JANUARY) || showStartMonth.equals(Month.FEBRUARY))) {
			showYear += 1;
		}

		LocalDate showDate = LocalDate.of(showYear, showStartMonth, showStartDay);

		return showDate.isAfter(latestShowDate);
	}
}
