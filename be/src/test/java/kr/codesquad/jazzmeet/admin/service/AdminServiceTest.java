package kr.codesquad.jazzmeet.admin.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import kr.codesquad.jazzmeet.IntegrationTestSupport;
import kr.codesquad.jazzmeet.admin.dto.request.SignUpAdminRequest;
import kr.codesquad.jazzmeet.admin.entity.Admin;
import kr.codesquad.jazzmeet.admin.entity.UserRole;
import kr.codesquad.jazzmeet.admin.repository.AdminRepository;
import kr.codesquad.jazzmeet.fixture.AdminFixture;
import kr.codesquad.jazzmeet.global.error.CustomException;
import kr.codesquad.jazzmeet.global.error.statuscode.AdminErrorCode;

class AdminServiceTest extends IntegrationTestSupport {

	@Autowired
	AdminService adminService;

	@Autowired
	AdminRepository adminRepository;

	@AfterEach
	void dbClean() {
		adminRepository.deleteAllInBatch();
	}

	@DisplayName("루트 관리자는 관리자 계정을 생성한다.")
	@Test
	void signUpAdmin() throws Exception {
		//given
		Admin root = AdminFixture.createAdmin("root1", "12345", UserRole.ROOT_ADMIN);
		adminRepository.save(root);

		Long rootId = root.getId();
		SignUpAdminRequest request = SignUpAdminRequest.builder()
			.loginId("admin1")
			.password("12345")
			.build();

		//when
		Admin savedAdmin = adminService.signUp(rootId, request);

		//then
		assertThat(savedAdmin)
			.extracting("loginId", "role")
			.contains("admin1", UserRole.ADMIN);
	}

	@DisplayName("루트 관리자가 아니라면 예외가 발생한다.")
	@Test
	void signUpFailWhenNotRoot() throws Exception {
		//given
		Admin root = AdminFixture.createAdmin("root1", "12345", UserRole.ADMIN);
		adminRepository.save(root);

		Long rootId = root.getId();
		SignUpAdminRequest request = SignUpAdminRequest.builder()
			.loginId("admin1")
			.password("12345")
			.build();

		//when //then
		assertThatThrownBy(() -> adminService.signUp(rootId, request))
			.isInstanceOf(CustomException.class)
			.hasMessage(AdminErrorCode.UNAUTHORIZED_ROLE.getMessage());
	}

	@DisplayName("루트 관리자가 계정을 생성할 때 이미 등록된 로그인 아이디라면 예외가 발생한다.")
	@Test
	void signUpFailWhenExistLoginId() throws Exception {
		//given
		String target = "root1";
		Admin root = AdminFixture.createAdmin(target, "12345", UserRole.ROOT_ADMIN);
		adminRepository.save(root);

		Long rootId = root.getId();
		SignUpAdminRequest request = SignUpAdminRequest.builder()
			.loginId(target)
			.password("12345")
			.build();

		//when //then
		assertThatThrownBy(() -> adminService.signUp(rootId, request))
			.isInstanceOf(CustomException.class)
			.hasMessage(AdminErrorCode.ALREADY_EXIST_ADMIN.getMessage());
	}
}