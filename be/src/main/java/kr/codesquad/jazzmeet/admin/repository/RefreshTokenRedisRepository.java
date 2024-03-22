package kr.codesquad.jazzmeet.admin.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import kr.codesquad.jazzmeet.admin.vo.RefreshToken;

public interface RefreshTokenRedisRepository extends CrudRepository<RefreshToken, String> {
	Optional<RefreshToken> findByRefreshToken(String refreshToken);
}
