package kr.codesquad.jazzmeet.image.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.codesquad.jazzmeet.global.error.CustomException;
import kr.codesquad.jazzmeet.global.error.statuscode.ImageErrorCode;
import kr.codesquad.jazzmeet.image.dto.response.ImageIdsResponse;
import kr.codesquad.jazzmeet.image.entity.Image;
import kr.codesquad.jazzmeet.image.entity.ImageStatus;
import kr.codesquad.jazzmeet.image.mapper.ImageMapper;
import kr.codesquad.jazzmeet.image.repository.ImageRepository;
import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ImageService {

	private final ImageRepository imageRepository;

	@Transactional
	public ImageIdsResponse saveImages(List<String> imageUrls) {
		List<Image> images = imageUrls.stream()
			.map(url -> {
				LocalDateTime now = LocalDateTime.now();
				return ImageMapper.INSTANCE.toImage(url, ImageStatus.UNREGISTERED, now);
			})
			.toList();

		List<Image> saveImages = imageRepository.saveAll(images);
		List<Long> ids = saveImages.stream()
			.map(Image::getId)
			.toList();

		return new ImageIdsResponse(ids);
	}

	@Transactional
	public void registerImage(Image image) {
		image.updateStatus(ImageStatus.REGISTERED);
	}

	@Transactional
	public void deleteImage(Long imageId) {
		Image image = findById(imageId);
		image.updateStatus(ImageStatus.DELETED);
	}

	public Image findById(Long imageId) {
		return imageRepository.findByIdAndStatusNot(imageId, ImageStatus.DELETED)
			.orElseThrow(() -> new CustomException(ImageErrorCode.NOT_FOUND_IMAGE));
	}
}
