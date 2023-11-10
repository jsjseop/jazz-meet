package kr.codesquad.jazzmeet.image.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import kr.codesquad.jazzmeet.image.dto.response.ImageIdsResponse;
import kr.codesquad.jazzmeet.image.service.CloudService;
import kr.codesquad.jazzmeet.image.service.ImageService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class ImageController {

	private final CloudService cloudService;
	private final ImageService imageService;

	/**
	 * 이미지 업로드 API
	 */
	@PostMapping("/api/images")
	public ResponseEntity<ImageIdsResponse> uploadImages(@RequestPart("image") List<MultipartFile> multipartFiles) {
		List<String> imageUrls = cloudService.uploadImages(multipartFiles);
		ImageIdsResponse imageIdsResponse = imageService.saveImages(imageUrls);

		return ResponseEntity.status(HttpStatus.CREATED).body(imageIdsResponse);
	}

	/**
	 * 이미지 삭제 API
	 */
	@DeleteMapping("/api/images/{imageId}")
	public ResponseEntity<Void> deleteImage(@PathVariable Long imageId) {
		imageService.deleteImage(imageId);

		return ResponseEntity.noContent().build();
	}
}
