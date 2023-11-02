package kr.codesquad.jazzmeet.fixture;

import java.time.LocalDateTime;

import kr.codesquad.jazzmeet.image.entity.Image;

public class ImageFixture {
	public static Image createImage(String imageUrl) {
		return Image.builder()
			.url(imageUrl)
			.status("registered")
			.createdAt(LocalDateTime.now())
			.build();
	}
}
