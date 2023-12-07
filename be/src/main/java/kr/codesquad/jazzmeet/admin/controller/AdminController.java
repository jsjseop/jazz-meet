package kr.codesquad.jazzmeet.admin.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import kr.codesquad.jazzmeet.admin.AdminMapper;
import kr.codesquad.jazzmeet.admin.dto.request.LoginAdminRequest;
import kr.codesquad.jazzmeet.admin.dto.response.LoginAdminResponse;
import kr.codesquad.jazzmeet.admin.dto.request.SignUpAdminRequest;
import kr.codesquad.jazzmeet.admin.service.AdminService;
import kr.codesquad.jazzmeet.global.jwt.Jwt;
import kr.codesquad.jazzmeet.global.jwt.JwtProperties;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class AdminController {

	private final AdminService adminService;
	private final JwtProperties jwtProperties;

	/**
	 * 관리자 계정 생성 API
	 */
	@PostMapping("/api/admins/sign-up")
	public ResponseEntity<Void> signUp(@RequestAttribute Long id,
		@Valid @RequestBody SignUpAdminRequest signUpAdminRequest) {
		adminService.signUp(id, signUpAdminRequest);

		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	/**
	 * 관리자 로그인 API
	 */
	@PostMapping("/api/admins/login")
	public ResponseEntity<LoginAdminResponse> login(@RequestBody @Valid LoginAdminRequest loginAdminRequest) {
		// Todo : refreshtoken이 재발급된 accesstoken인지 확인하는 절차 필요

		Jwt jwt = adminService.login(loginAdminRequest);

		ResponseCookie cookie = ResponseCookie.from("refreshToken", jwt.getRefreshToken())
			.maxAge(jwtProperties.getRefreshTokenExpiration())
			.path("/")
			.secure(true)
			.httpOnly(true)
			.build();

		return ResponseEntity.ok()
			.header(HttpHeaders.SET_COOKIE, cookie.toString())
			.body(AdminMapper.INSTANCE.toLoginAdminResponse(jwt));
	}
}
