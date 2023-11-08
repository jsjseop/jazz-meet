package kr.codesquad.jazzmeet.fixture;

import java.time.LocalDateTime;

import kr.codesquad.jazzmeet.image.entity.Image;
import kr.codesquad.jazzmeet.image.entity.ImageStatus;

public class ImageFixture {
	public static Image createImage(String imageUrl) {
		return Image.builder()
			.url(imageUrl)
			.status(ImageStatus.UNREGISTERED)
			.createdAt(LocalDateTime.now())
			.build();
	}
}
