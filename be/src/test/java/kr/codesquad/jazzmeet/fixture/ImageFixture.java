package kr.codesquad.jazzmeet.fixture;

import kr.codesquad.jazzmeet.image.entity.Image;
import kr.codesquad.jazzmeet.image.entity.ImageStatus;

public class ImageFixture {
	public static Image createImage(String imageUrl) {
		return Image.builder()
			.url(imageUrl)
			.status(ImageStatus.UNREGISTERED)
			.build();
	}

	public static Image createImage(String imageUrl, ImageStatus status) {
		return Image.builder()
			.url(imageUrl)
			.status(status)
			.build();
	}
}
