package kr.codesquad.jazzmeet.fixture;

import kr.codesquad.jazzmeet.admin.entity.Admin;
import kr.codesquad.jazzmeet.admin.entity.UserRole;

public class AdminFixture {

	public static Admin createAdmin(String loginId, String password, UserRole role) {
		return Admin.builder()
			.loginId(loginId)
			.password(password)
			.role(role)
			.build();
	}
}
