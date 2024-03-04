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

import kr.codesquad.jazzmeet.global.permission.Permission;
import kr.codesquad.jazzmeet.image.dto.response.ImageCreateResponse;
import kr.codesquad.jazzmeet.image.entity.ImageStatus;
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
	@Permission
	@PostMapping("/api/images")
	public ResponseEntity<ImageCreateResponse> uploadImages(@RequestPart("image") List<MultipartFile> multipartFiles) {
		List<String> imageUrls = cloudService.uploadImages(multipartFiles);
		ImageStatus imageStatus = ImageStatus.UNREGISTERED;
		ImageCreateResponse imageCreateResponse = imageService.saveImages(imageUrls, imageStatus);

		return ResponseEntity.status(HttpStatus.CREATED).body(imageCreateResponse);
	}

	/**
	 * 이미지 삭제 API
	 */
	@Permission
	@DeleteMapping("/api/images/{imageId}")
	public ResponseEntity<Void> deleteImage(@PathVariable Long imageId) {
		imageService.deleteImage(imageId);

		return ResponseEntity.noContent().build();
	}
}
