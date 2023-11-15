package kr.codesquad.jazzmeet.image.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.codesquad.jazzmeet.global.error.CustomException;
import kr.codesquad.jazzmeet.global.error.statuscode.ImageErrorCode;
import kr.codesquad.jazzmeet.image.dto.response.ImageIdsResponse;
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
	public ImageIdsResponse saveImages(List<String> imageUrls) {
		List<Image> images = imageUrls.stream()
			.map(url -> ImageMapper.INSTANCE.toImage(url, ImageStatus.UNREGISTERED))
			.toList();

		List<Image> saveImages = imageRepository.saveAll(images);
		List<Long> ids = saveImages.stream()
			.map(Image::getId)
			.toList();

		return new ImageIdsResponse(ids);
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

	public List<String> findNotRegisteredImageUrls() {
		List<Image> images = imageRepository.findAllByStatusNot(ImageStatus.REGISTERED);
		return images.stream()
			.map(Image::getUrl)
			.toList();
	}

	@Transactional
	public void deleteImagesByUrls(List<String> urls) {
		imageQueryRepository.deleteAllInUrls(urls);
	}
}
