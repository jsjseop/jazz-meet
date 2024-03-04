package kr.codesquad.jazzmeet.admin.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import kr.codesquad.jazzmeet.admin.mapper.AdminMapper;
import kr.codesquad.jazzmeet.admin.dto.request.LoginAdminRequest;
import kr.codesquad.jazzmeet.admin.dto.request.SignUpAdminRequest;
import kr.codesquad.jazzmeet.admin.dto.response.LoginAdminResponse;
import kr.codesquad.jazzmeet.admin.entity.Admin;
import kr.codesquad.jazzmeet.admin.service.AdminService;
import kr.codesquad.jazzmeet.global.error.CustomException;
import kr.codesquad.jazzmeet.global.error.statuscode.AdminErrorCode;
import kr.codesquad.jazzmeet.global.jwt.Jwt;
import kr.codesquad.jazzmeet.global.jwt.JwtProperties;
import kr.codesquad.jazzmeet.global.permission.AdminAuth;
import kr.codesquad.jazzmeet.global.permission.Permission;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
			.httpOnly(false)
			.domain(".jazzmeet.site")
			// .sameSite("None")
			.build();
	}

	/**
	 * 관리자 토큰 재발급 API
	 */
	@PostMapping("/api/admins/reissue")
	public ResponseEntity<LoginAdminResponse> reissueToken(HttpServletRequest request) {

		log.warn("cookies: {}", request.getCookies());
		String refreshToken = extractRefreshToken(request.getCookies());

		Jwt jwt = adminService.reissue(refreshToken);

		return ResponseEntity.ok()
			.header(HttpHeaders.SET_COOKIE, getRefreshToken(jwt).toString())
			.body(AdminMapper.INSTANCE.toLoginAdminResponse(jwt));
	}

	private String extractRefreshToken(Cookie[] cookies) {
		if (cookies == null)
			throw new CustomException(AdminErrorCode.NOT_EXIST_COOKIE);
		for (Cookie cookie : cookies) {
			if ("refreshToken".equals(cookie.getName())) {
				return cookie.getValue();
			}
		}
		throw new CustomException(AdminErrorCode.NOT_FOUND_TOKEN_COOKIE);
	}

	/**
	 * 관리자 계정 로그아웃 API
	 */
	@Permission
	@PostMapping("/api/admins/logout")
	public ResponseEntity<Void> logout(@AdminAuth Admin admin, @RequestAttribute String accessToken) {
		adminService.logout(admin, accessToken);
		return ResponseEntity.ok().build();
	}

}
