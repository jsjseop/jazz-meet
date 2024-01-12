package kr.codesquad.jazzmeet.global.util;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import lombok.RequiredArgsConstructor;

/*
	- 크롬 드라이버 생성, 크롬 옵션 설정, 크롬 드라이버의 설치 경로 WEB_DRIVER_PATH 설정
	‼️ 크롬과 드라이버의 버전이 동일해야 한다
 */
@RequiredArgsConstructor
@Component
public class WebDriverUtil {
	private static final String WEB_DRIVER_ID = "webdriver.chrome.driver";
	private static String WEB_DRIVER_PATH; // WebDriver 경로

	public static WebDriver getChromeDriver() {
		// webDriver 설정
		if (ObjectUtils.isEmpty(System.getProperty(WEB_DRIVER_ID))) {
			System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);
		}
		// 크롬 설정을 담은 객체 생성
		ChromeOptions chromeOptions = new ChromeOptions();
		// 브라우저가 눈에 보이지 않고 내부적으로 실행된다.
		chromeOptions.addArguments("headless");
		chromeOptions.addArguments("--lang=ko");
		chromeOptions.addArguments("--no-sandbox");
		chromeOptions.addArguments("--disable-dev-shm-usage");
		chromeOptions.addArguments("--disable-gpu");
		chromeOptions.setCapability("ignoreProtectedModeSettings", true);
		// 위 옵션을 담은 드라이버 객체 생성.
		// 옵션을 설정하지 않았다면 생략 가능하다.
		// ⭐️ WebDriver를 크롬으로 생각하고 사용한다.
		WebDriver driver = new ChromeDriver(chromeOptions);
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));

		return driver;
	}

	@Value("${driver.chrome.driver.path}")
	public void setWebDriverPath(String path) {
		WEB_DRIVER_PATH = path;
	}

	public static void quit(WebDriver driver) {
		if (!ObjectUtils.isEmpty(driver)) {
			driver.quit();
		}
	}

	public static void close(WebDriver driver) {
		if (!ObjectUtils.isEmpty(driver)) {
			driver.close();
		}
	}
}
