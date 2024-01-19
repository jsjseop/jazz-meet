package kr.codesquad.jazzmeet.global.util;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Component;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class WebDriverUtil {

	public static WebDriver getChromeDriver() {
		/* 웹 드라이버 최신으로 설치.
		(‼️ 크롬과 드라이버의 버전이 동일해야 한다.) */
		WebDriverManager.chromedriver().setup();
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

	public static void quit(WebDriver driver) {
		driver.quit();
	}

	public static void close(WebDriver driver) {
		driver.close();
	}
}
