package kr.codesquad.jazzmeet.image.service;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import kr.codesquad.jazzmeet.IntegrationTestSupport;
import kr.codesquad.jazzmeet.fixture.ImageFixture;
import kr.codesquad.jazzmeet.global.error.CustomException;
import kr.codesquad.jazzmeet.image.dto.response.ImageIdsResponse;
import kr.codesquad.jazzmeet.image.entity.Image;
import kr.codesquad.jazzmeet.image.entity.ImageStatus;
import kr.codesquad.jazzmeet.image.repository.ImageRepository;

class ImageServiceTest extends IntegrationTestSupport {

	@Autowired
	ImageService imageService;

	@Autowired
	ImageRepository imageRepository;

	@AfterEach
	void dbClean() {
		imageRepository.deleteAllInBatch();
	}

	@Test
	@DisplayName("이미지 URL의 리스트를 받아서 저장하고 이미지 ID의 리스트를 반환한다")
	void saveImages() {
	    // given
		List<String> imageUrls = List.of("imgUrl1", "imgUrl2", "imgUrl3");

		// when
		ImageIdsResponse imageIdsResponse = imageService.saveImages(imageUrls);

		// then
		assertThat(imageIdsResponse.ids()).hasSize(imageUrls.size());
	}

	@Test
	@DisplayName("이미지의 상태를 삭제 상태로 변경한다")
	void deleteImage() {
	    // given
		Image image = ImageFixture.createImage("imgUrl");
		Image savedImage = imageRepository.save(image);

		// when
		imageService.deleteImage(savedImage.getId());

	    // then
		Image deletedImage = imageRepository.findById(savedImage.getId()).get();
		assertThat(deletedImage).extracting("status").isEqualTo(ImageStatus.DELETED);
	}

	@Test
	@DisplayName("존재하지 않는 아이디로 이미지를 삭제하려 하면 삭제되지 않는다")
	void deleteImageWrongId() {
		// given
		Long wrongId = -1L;

		// when then
		assertThatThrownBy(() -> imageService.deleteImage(wrongId))
			.isInstanceOf(CustomException.class);
	}
}
