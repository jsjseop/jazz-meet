package kr.codesquad.jazzmeet.image.repository;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import kr.codesquad.jazzmeet.IntegrationTestSupport;
import kr.codesquad.jazzmeet.fixture.ImageFixture;
import kr.codesquad.jazzmeet.image.entity.Image;
import kr.codesquad.jazzmeet.image.entity.ImageStatus;

class ImageRepositoryTest extends IntegrationTestSupport {

	@Autowired
	ImageQueryRepository imageQueryRepository;

	@Autowired
	ImageRepository imageRepository;

	@AfterEach
	void dbClean() {
		imageRepository.deleteAllInBatch();
	}

	@Test
	@DisplayName("이미지의 상태가 DELETED 이거나 상태가 UNREGISTERED 이면서 생성일자가 오늘이 아닌 이미지를 모두 조회한다")
	void findAllStatusNotRegistered() {
		// given
		Image image1 = ImageFixture.createImage("url1", ImageStatus.REGISTERED);
		Image image2 = ImageFixture.createImage("url2", ImageStatus.UNREGISTERED);
		Image image3 = ImageFixture.createImage("url3", ImageStatus.DELETED);

		imageRepository.saveAll(List.of(image1, image2, image3));

		LocalDate today = LocalDate.now();
		LocalDate yesterday = today.minusDays(1);

		// when
		List<Image> images = imageQueryRepository.findAllNotRegistered(yesterday);

		// then
		assertThat(images).extracting("url")
			.containsExactly("url2", "url3");
	}

	@Test
	@DisplayName("URL의 리스트를 입력 받아서 URL에 해당하는 이미지를 모두 삭제한다")
	@Transactional
	void deleteImagesByUrls() {
		// given
		Image image1 = ImageFixture.createImage("url1");
		Image image2 = ImageFixture.createImage("url2");
		Image image3 = ImageFixture.createImage("url3");

		imageRepository.saveAll(List.of(image1, image2, image3));
		List<String> imageUrls = List.of(image1.getUrl(), image2.getUrl());

		// when
		imageQueryRepository.deleteAllInUrls(imageUrls);

		// then
		List<Image> images = imageRepository.findAll();
		assertThat(images).extracting("url")
			.containsExactly("url3");
	}
}
