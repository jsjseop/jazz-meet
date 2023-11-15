package kr.codesquad.jazzmeet.admin.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import kr.codesquad.jazzmeet.admin.dto.request.SignUpAdminRequest;
import kr.codesquad.jazzmeet.admin.service.AdminService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class AdminController {

	private final AdminService adminService;

	/**
	 * 관리자 계정 생성 API
	 */
	@PostMapping("/api/admins/sign-up")
	public ResponseEntity<Void> signUp(@RequestAttribute Long id,
		@Valid @RequestBody SignUpAdminRequest signUpAdminRequest) {
		adminService.signUp(id, signUpAdminRequest);

		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
}
