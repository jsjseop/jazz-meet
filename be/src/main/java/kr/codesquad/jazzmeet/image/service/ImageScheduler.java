package kr.codesquad.jazzmeet.image.service;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class ImageScheduler {

	private final ImageService imageService;
	private final CloudService cloudService;

	@Scheduled(cron = "0 0 12 ? * MON")
	public void deleteNotRegisteredImages() {
		List<String> imageUrls = imageService.findNotRegisteredImageUrls();

		List<String> deletedImageUrls = cloudService.deleteImages(imageUrls);

		imageService.deleteImagesByUrls(deletedImageUrls);
	}
}
