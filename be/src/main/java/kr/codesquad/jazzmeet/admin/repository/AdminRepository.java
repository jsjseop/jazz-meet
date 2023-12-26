package kr.codesquad.jazzmeet.admin.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import kr.codesquad.jazzmeet.admin.entity.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

	Optional<Admin> findByLoginId(String loginId);

	boolean existsByLoginId(String loginId);

	Optional<Admin> findByRefreshToken(String refreshToken);

	@Modifying
	@Query("update Admin a set a.refreshToken = null where a.id = :adminId")
	void deleteRefreshTokenById(@Param("adminId") Long adminId);
}
