package kr.codesquad.jazzmeet.image.repository;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import kr.codesquad.jazzmeet.global.error.CustomException;
import kr.codesquad.jazzmeet.global.error.statuscode.ErrorCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class S3ImageHandler {

	private static final String IMAGE_DIRECTORY = "/images";

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
			String fileName = file.getOriginalFilename();
			ObjectMetadata objectMetadata = new ObjectMetadata();
			objectMetadata.setContentLength(file.getSize());
			objectMetadata.setContentType(file.getContentType());

			amazonS3Client.putObject(
				new PutObjectRequest(bucket + dir, fileName, inputStream, objectMetadata)
					.withCannedAcl(CannedAccessControlList.PublicRead));	// PublicRead 권한으로 업로드
			return amazonS3Client.getUrl(bucket + dir, fileName).toString();
		} catch (IOException e) {
			throw new CustomException(ErrorCode.IMAGE_UPLOAD_ERROR);
		}
	}
}
