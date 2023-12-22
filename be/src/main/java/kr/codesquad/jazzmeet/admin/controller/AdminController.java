package kr.codesquad.jazzmeet.admin.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import kr.codesquad.jazzmeet.admin.AdminMapper;
import kr.codesquad.jazzmeet.admin.dto.request.LoginAdminRequest;
import kr.codesquad.jazzmeet.admin.dto.request.ReissueAdminRequest;
import kr.codesquad.jazzmeet.admin.dto.request.SignUpAdminRequest;
import kr.codesquad.jazzmeet.admin.dto.response.LoginAdminResponse;
import kr.codesquad.jazzmeet.admin.entity.Admin;
import kr.codesquad.jazzmeet.admin.service.AdminService;
import kr.codesquad.jazzmeet.global.jwt.Jwt;
import kr.codesquad.jazzmeet.global.jwt.JwtProperties;
import kr.codesquad.jazzmeet.global.permission.AdminAuth;
import kr.codesquad.jazzmeet.global.permission.Permission;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class AdminController {

	private final AdminService adminService;
	private final JwtProperties jwtProperties;

	/**
	 * 관리자 계정 생성 API
	 */
	@Permission
	@PostMapping("/api/admins/sign-up")
	public ResponseEntity<Void> signUp(@AdminAuth Admin rootAdmin,
		@Valid @RequestBody SignUpAdminRequest signUpAdminRequest) {
		adminService.signUp(rootAdmin, signUpAdminRequest);

		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	/**
	 * 관리자 로그인 API
	 */
	@PostMapping("/api/admins/login")
	public ResponseEntity<LoginAdminResponse> login(@RequestBody @Valid LoginAdminRequest loginAdminRequest) {
		// Todo : refreshtoken이 재발급된 accesstoken인지 확인하는 절차 필요

		Jwt jwt = adminService.login(loginAdminRequest);

		return ResponseEntity.ok()
			.header(HttpHeaders.SET_COOKIE, getRefreshToken(jwt).toString())
			.body(AdminMapper.INSTANCE.toLoginAdminResponse(jwt));
	}

	private ResponseCookie getRefreshToken(Jwt jwt) {
		return ResponseCookie.from("refreshToken", jwt.getRefreshToken())
			.maxAge(jwtProperties.getRefreshTokenExpiration())
			.path("/")
			.secure(true)
			.httpOnly(true)
			.build();
	}

	/**
	 * 관리자 토큰 재발급 API
	 */
	@PostMapping("/api/admins/reissue")
	public ResponseEntity<LoginAdminResponse> reissueToken(@RequestBody @Valid ReissueAdminRequest reissueAdminRequest) {

		Jwt jwt = adminService.reissue(reissueAdminRequest);

		return ResponseEntity.ok()
			.header(HttpHeaders.SET_COOKIE, getRefreshToken(jwt).toString())
			.body(AdminMapper.INSTANCE.toLoginAdminResponse(jwt));
	}

	/**
	 * 관리자 계정 로그아웃 API
	 */
	@Permission
	@PostMapping("/api/admins/logout")
	public ResponseEntity<Void> logout(@AdminAuth Admin admin) {
		adminService.logout(admin);
		return ResponseEntity.ok().build();
	}

}
