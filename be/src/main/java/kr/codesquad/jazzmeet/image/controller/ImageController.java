package kr.codesquad.jazzmeet.image.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import kr.codesquad.jazzmeet.image.dto.response.ImageIdsResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class ImageController {


	@PostMapping("/api/images")
	public ResponseEntity<ImageIdsResponse> uploadImages(@RequestPart("image") List<MultipartFile> multipartFiles) {
		return null;
	}
}
