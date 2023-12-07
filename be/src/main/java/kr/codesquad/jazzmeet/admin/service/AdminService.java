package kr.codesquad.jazzmeet.admin.service;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.codesquad.jazzmeet.admin.AdminMapper;
import kr.codesquad.jazzmeet.admin.dto.request.LoginAdminRequest;
import kr.codesquad.jazzmeet.admin.dto.request.SignUpAdminRequest;
import kr.codesquad.jazzmeet.admin.entity.Admin;
import kr.codesquad.jazzmeet.admin.entity.UserRole;
import kr.codesquad.jazzmeet.admin.repository.AdminRepository;
import kr.codesquad.jazzmeet.global.error.CustomException;
import kr.codesquad.jazzmeet.global.error.statuscode.AdminErrorCode;
import kr.codesquad.jazzmeet.global.jwt.Jwt;
import kr.codesquad.jazzmeet.global.jwt.JwtProvider;
import kr.codesquad.jazzmeet.global.util.PasswordEncoder;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdminService {

	private static final String ADMIN_ID = "adminId";

	private final AdminRepository adminRepository;
	private final JwtProvider jwtProvider;

	@Transactional
	public Admin signUp(Long rootAdminId, SignUpAdminRequest signUpAdminRequest) {
		Admin root = findAdminById(rootAdminId);
		root.isRoot();
		isExistAdmin(signUpAdminRequest.loginId());

		String encodedPassword = PasswordEncoder.encode(signUpAdminRequest.password());
		Admin admin = AdminMapper.INSTANCE.toAdmin(signUpAdminRequest, encodedPassword, UserRole.ADMIN);

		return adminRepository.save(admin);
	}

	private void isExistAdmin(String loginId) {
		if (findAdminByLoginId(loginId) != null) {
			throw new CustomException(AdminErrorCode.ALREADY_EXIST_ADMIN);
		}
	}

	private Admin findAdminByLoginId(String loginId) {
		return adminRepository.findByLoginId(loginId)
			.orElseThrow(() -> new CustomException(AdminErrorCode.NOT_FOUND_ADMIN));
	}

	public Admin findAdminById(Long rootAdminId) {
		return adminRepository.findById(rootAdminId)
			.orElseThrow(() -> new CustomException(AdminErrorCode.NOT_FOUND_ADMIN));
	}

	@Transactional
	public Jwt login(LoginAdminRequest loginAdminRequest) {
		Admin admin = findAdminByLoginId(loginAdminRequest.loginId());
		admin.isSame(loginAdminRequest.password());

		Map<String, Object> claims = Map.of(ADMIN_ID, admin.getId());
		Jwt jwt = jwtProvider.createJwt(claims);
		admin.update(jwt.getRefreshToken());

		return jwt;
	}
}
