package kr.codesquad.jazzmeet.image.repository;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;

import kr.codesquad.jazzmeet.IntegrationTestSupport;
import kr.codesquad.jazzmeet.global.error.CustomException;

class S3ImageHandlerTest extends IntegrationTestSupport {

	@Autowired
	S3ImageHandler s3ImageHandler;

	@Test
	@DisplayName("S3에 이미지를 업로드한다")
	void uploadImage() {
	    // given
		byte[] content = "TEST CONTENT".getBytes();
		MockMultipartFile file = new MockMultipartFile("image", "fileName.jpg", "multipart/form-data", content);

		// when
		String imageUrl = s3ImageHandler.uploadImage(file, "/test");

		// then
		assertThat(imageUrl).isNotNull();
	}

	@Test
	@DisplayName("파일명이 잘못된 형식의 이미지는 업로도 되지 않는다")
	void uploadImageWrongFormat() {
		// given
		byte[] content = "TEST CONTENT".getBytes();
		MockMultipartFile file1 = new MockMultipartFile("image", "fileName.txt", "multipart/form-data", content);
		MockMultipartFile file2 = new MockMultipartFile("image", "", "multipart/form-data", content);

		// when then
		assertThatThrownBy(() -> s3ImageHandler.uploadImage(file1, "/test"))
			.isInstanceOf(CustomException.class);
		assertThatThrownBy(() -> s3ImageHandler.uploadImage(file2, "/test"))
			.isInstanceOf(CustomException.class);
	}
}
