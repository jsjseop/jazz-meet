package kr.codesquad.jazzmeet.admin.service;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.codesquad.jazzmeet.admin.AdminMapper;
import kr.codesquad.jazzmeet.admin.dto.request.SignUpAdminRequest;
import kr.codesquad.jazzmeet.admin.entity.Admin;
import kr.codesquad.jazzmeet.admin.entity.UserRole;
import kr.codesquad.jazzmeet.admin.repository.AdminRepository;
import kr.codesquad.jazzmeet.global.error.CustomException;
import kr.codesquad.jazzmeet.global.error.statuscode.AdminErrorCode;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdminService {

	private final AdminRepository adminRepository;
	private final BCryptPasswordEncoder encoder;

	@Transactional
	public Admin signUp(Long rootAdminId, SignUpAdminRequest signUpAdminRequest) {
		Admin root = findAdminById(rootAdminId);
		root.isRoot();
		isExistAdmin(signUpAdminRequest.loginId());

		String encodedPassword = encoder.encode(signUpAdminRequest.password());
		Admin admin = AdminMapper.INSTANCE.toAdmin(signUpAdminRequest, encodedPassword, UserRole.ADMIN);

		return adminRepository.save(admin);
	}

	private void isExistAdmin(String loginId) {
		if (!findAdminByName(loginId).isEmpty()) {
			throw new CustomException(AdminErrorCode.ALREADY_EXIST_ADMIN);
		}
	}

	private Optional<Admin> findAdminByName(String loginId) {
		return adminRepository.findByLoginId(loginId);
	}

	public Admin findAdminById(Long rootAdminId) {
		return adminRepository.findById(rootAdminId)
			.orElseThrow(() -> new CustomException(AdminErrorCode.NOT_FOUND_ADMIN));
	}
}
