package kr.codesquad.jazzmeet.image.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import kr.codesquad.jazzmeet.global.error.CustomException;
import kr.codesquad.jazzmeet.global.error.statuscode.ImageErrorCode;
import kr.codesquad.jazzmeet.global.util.CustomMultipartFile;
import kr.codesquad.jazzmeet.image.dto.response.ImageCreateResponse;
import kr.codesquad.jazzmeet.image.dto.response.ImageSaveResponse;
import kr.codesquad.jazzmeet.image.entity.Image;
import kr.codesquad.jazzmeet.image.entity.ImageStatus;
import kr.codesquad.jazzmeet.image.mapper.ImageMapper;
import kr.codesquad.jazzmeet.image.repository.ImageQueryRepository;
import kr.codesquad.jazzmeet.image.repository.ImageRepository;
import kr.codesquad.jazzmeet.image.repository.S3ImageHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ImageService {

	private final ImageRepository imageRepository;
	private final ImageQueryRepository imageQueryRepository;
	private final S3ImageHandler s3ImageHandler;

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

	public List<Long> uploadPosters(List<String> posterUrls) {
		// Image 가져오기
		List<MultipartFile> multipartFiles = convertImageUrlToMultipartFile(posterUrls);
		// S3에 사진 저장
		List<String> uploadedImageUrls = s3ImageHandler.uploadImages(multipartFiles);
		// DB에 저장
		ImageStatus imageStatus = ImageStatus.REGISTERED;
		ImageCreateResponse imageCreateResponse = saveImages(uploadedImageUrls, imageStatus);

		return imageCreateResponse.images().stream().map(ImageSaveResponse::id).toList();
	}

	private List<MultipartFile> convertImageUrlToMultipartFile(List<String> imageUrls) {
		List<MultipartFile> multipartFiles = new ArrayList<>();
		for (int i = 0; i < imageUrls.size(); i++) {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			try {
				BufferedImage image = ImageIO.read(new URL(imageUrls.get(i)));
				ImageIO.write(image, "jpeg", out);
			} catch (IOException e) {
				log.error("IO Error", e);
				return null;
			}
			byte[] bytes = out.toByteArray();
			CustomMultipartFile customMultipartFile = new CustomMultipartFile(bytes, "image" + i,
				"posterImage" + i + ".jpeg",
				"jpeg", bytes.length);
			multipartFiles.add(customMultipartFile);
		}
		return multipartFiles;
	}
}
