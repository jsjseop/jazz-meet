package kr.codesquad.jazzmeet.admin.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import kr.codesquad.jazzmeet.global.error.CustomException;
import kr.codesquad.jazzmeet.global.error.statuscode.AdminErrorCode;
import kr.codesquad.jazzmeet.global.util.PasswordEncoder;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Admin {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true, nullable = false, length = 20)
	private String loginId;
	@Column(nullable = false, length = 200)
	private String password;

	@Enumerated(value = EnumType.STRING)
	@Column(nullable = false, length = 10)
	private UserRole role;

	@Column(length = 500)
	private String refreshToken;

	@Builder
	public Admin(String loginId, String password, UserRole role) {
		this.loginId = loginId;
		this.password = password;
		this.role = role;
	}

	public void isRoot() {
		if (this.role != UserRole.ROOT_ADMIN) {
			throw new CustomException(AdminErrorCode.UNAUTHORIZED_ROLE);
		}
	}

	public void isSame(String password) {
		PasswordEncoder.matchesPassword(password, this.password);
	}

	public void update(String refreshToken) {
		this.refreshToken = refreshToken;
	}
}
