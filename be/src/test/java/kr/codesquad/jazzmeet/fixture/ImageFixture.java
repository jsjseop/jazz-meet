package kr.codesquad.jazzmeet.fixture;

import java.time.LocalDateTime;

import kr.codesquad.jazzmeet.image.entity.Image;
import kr.codesquad.jazzmeet.image.entity.Status;

public class ImageFixture {
	public static Image createImage(String imageUrl) {
		return Image.builder()
			.url(imageUrl)
			.status(Status.UNREGISTERED)
			.createdAt(LocalDateTime.now())
			.build();
	}
}
