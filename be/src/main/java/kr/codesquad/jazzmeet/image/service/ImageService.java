package kr.codesquad.jazzmeet.image.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.codesquad.jazzmeet.global.error.CustomException;
import kr.codesquad.jazzmeet.global.error.statuscode.ImageErrorCode;
import kr.codesquad.jazzmeet.image.dto.response.ImageCreateResponse;
import kr.codesquad.jazzmeet.image.dto.response.ImageSaveResponse;
import kr.codesquad.jazzmeet.image.entity.Image;
import kr.codesquad.jazzmeet.image.entity.ImageStatus;
import kr.codesquad.jazzmeet.image.mapper.ImageMapper;
import kr.codesquad.jazzmeet.image.repository.ImageQueryRepository;
import kr.codesquad.jazzmeet.image.repository.ImageRepository;
import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ImageService {

	private final ImageRepository imageRepository;
	private final ImageQueryRepository imageQueryRepository;

	@Transactional
	public ImageCreateResponse saveImages(List<String> imageUrls, ImageStatus imageStatus) {
		List<Image> images = imageUrls.stream()
			.map(url -> ImageMapper.INSTANCE.toImage(url, imageStatus))
			.toList();

		List<Image> saveImages = imageRepository.saveAll(images);
		List<ImageSaveResponse> imageSaveResponses = saveImages.stream()
			.map(ImageMapper.INSTANCE::toImageSaveResponse)
			.toList();

		return new ImageCreateResponse(imageSaveResponses);
	}

	@Transactional
	public void deleteImage(Long imageId) {
		Image image = findById(imageId);
		image.delete();
	}

	public Image findById(Long imageId) {
		return imageRepository.findByIdAndStatusNot(imageId, ImageStatus.DELETED)
			.orElseThrow(() -> new CustomException(ImageErrorCode.NOT_FOUND_IMAGE));
	}

	public Image findByIdExistDeleted(Long imageId) {
		return imageRepository.findById(imageId)
			.orElseThrow(() -> new CustomException(ImageErrorCode.NOT_FOUND_IMAGE));
	}

	public List<String> findNotRegisteredImageUrls() {
		LocalDate today = LocalDate.now();
		// UNREGISTERED 상태는 created_at이 today와 다른 데이터 조회
		List<Image> images = imageQueryRepository.findAllNotRegistered(today);
		return images.stream()
			.map(Image::getUrl)
			.toList();
	}

	@Transactional
	public void deleteImagesByUrls(List<String> urls) {
		imageQueryRepository.deleteAllInUrls(urls);
	}
}
