package kr.codesquad.jazzmeet.image.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import kr.codesquad.jazzmeet.image.repository.S3ImageHandler;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CloudService {

	private final S3ImageHandler s3ImageHandler;

	public List<String> uploadImages(List<MultipartFile> multipartFiles) {
		return s3ImageHandler.uploadImages(multipartFiles);
	}
}
