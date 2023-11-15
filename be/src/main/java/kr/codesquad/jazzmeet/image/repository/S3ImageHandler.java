package kr.codesquad.jazzmeet.image.repository;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import kr.codesquad.jazzmeet.global.error.CustomException;
import kr.codesquad.jazzmeet.global.error.statuscode.ImageErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Component
@Slf4j
public class S3ImageHandler {

	private static final String IMAGE_DIRECTORY = "images/";
	public static final int FILE_NAME_SEPARATE_INDEX = 4;

	private final AmazonS3Client amazonS3Client;

	@Value("${cloud.aws.s3.bucket}")
	private String bucket;

	public List<String> uploadImages(List<MultipartFile> multipartFiles) {
		List<String> imageUrls = new ArrayList<>();
		for (MultipartFile file : multipartFiles) {
			String imageUrl = uploadImage(file, IMAGE_DIRECTORY);
			imageUrls.add(imageUrl);
		}
		return imageUrls;
	}

	public String uploadImage(MultipartFile file, String dir) {
		try (InputStream inputStream = file.getInputStream()) {
			String fileName = createFileName(file.getOriginalFilename());
			ObjectMetadata objectMetadata = new ObjectMetadata();
			objectMetadata.setContentLength(file.getSize());
			objectMetadata.setContentType(file.getContentType());

			amazonS3Client.putObject(
				new PutObjectRequest(bucket, dir + fileName, inputStream, objectMetadata)
					.withCannedAcl(CannedAccessControlList.PublicRead));	// PublicRead 권한으로 업로드
			return amazonS3Client.getUrl(bucket, dir + fileName).toString();
		} catch (IOException e) {
			throw new CustomException(ImageErrorCode.IMAGE_UPLOAD_ERROR);
		}
	}

	// 파일명 중복 방지
	private String createFileName(String fileName) {
		validateFileName(fileName);
		return UUID.randomUUID() + fileName;
	}

	// 파일 확장자 유효성 검사
	private void validateFileName(String fileName) {
		if (isExistFileName(fileName)) {
			throw new CustomException(ImageErrorCode.NOT_FOUND_IMAGE);
		}

		String extension = getFileExtension(fileName);
		List<String> allowedExtension = List.of("png", "jpg", "jpeg");
		if (!allowedExtension.contains(extension)) {
			throw new CustomException(ImageErrorCode.WRONG_IMAGE_FORMAT);
		}
	}

	private boolean isExistFileName(String fileName) {
		return fileName == null || fileName.equals("");
	}

	private String getFileExtension(String fileName) {
		int lastDotIndex = fileName.lastIndexOf('.');
		if (lastDotIndex == -1) {
			throw new CustomException(ImageErrorCode.WRONG_IMAGE_FORMAT);
		}
		return fileName.substring(lastDotIndex + 1).toLowerCase();
	}

	public List<String> deleteImages(List<String> imageUrls) {
		try {
			String[] urls = imageUrls.stream()
				.map(url -> IMAGE_DIRECTORY + url.split("/")[FILE_NAME_SEPARATE_INDEX])
				.toArray(String[]::new);
			DeleteObjectsRequest deleteObjectsRequest = new DeleteObjectsRequest(bucket).withKeys(urls);

			amazonS3Client.deleteObjects(deleteObjectsRequest);
		} catch (Exception e) {
			throw new CustomException(ImageErrorCode.IMAGE_DELETE_ERROR);
		}
		return imageUrls;
	}
}
