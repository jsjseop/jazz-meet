package kr.codesquad.jazzmeet.global.jwt;

import static org.assertj.core.api.Assertions.*;

import java.util.Base64;
import java.util.Date;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

@SpringBootTest
class JwtProviderTest {

	@Autowired
	JwtProperties jwtProperties;

	@Autowired
	JwtProvider jwtProvider;

	@DisplayName("jwt를 생성한다.")
	@Test
	void createJwt() throws Exception {
	    //given
		Map<String, Object> claims = Map.of("adminId", 1L);

	    //when
		Jwt jwt = jwtProvider.createJwt(claims);

		Claims accessToken = jwtProvider.getClaims(jwt.getAccessToken());
		Claims refreshToken = jwtProvider.getClaims(jwt.getRefreshToken());

		//then
		assertThat(accessToken.get("adminId")).isEqualTo(1);
		assertThat(accessToken.getExpiration().getTime()).isLessThan(
			System.currentTimeMillis() + jwtProperties.getAccessTokenExpiration());

		assertThat(refreshToken.get("adminId")).isNull();
		assertThat(refreshToken.getExpiration().getTime()).isLessThan(
			System.currentTimeMillis() + jwtProperties.getRefreshTokenExpiration());
	}

	@DisplayName("잘못된 JWT 서명으로 인한 예외를 테스트한다.")
	@Test
	void testInvalidJwtSignature() throws Exception {
		// given
		String invalidToken = Jwts.builder()
			.setClaims(Map.of("admin1", 1L))
			.setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getAccessTokenExpiration()))
			.signWith(SignatureAlgorithm.HS256,
				Base64.getEncoder().encodeToString("invalidSecretKey".repeat(3).getBytes()))
			.compact();

		// then
		assertThatThrownBy(() -> jwtProvider.getClaims(invalidToken))
			.isInstanceOf(SignatureException.class);
	}

	@DisplayName("만료된 토큰 처리를 테스트한다.")
	@Test
	void testExpiredToken() throws Exception {
		// given
		Map<String, Object> expiredClaims = Map.of("adminId", 1L);
		String expiredToken = jwtProvider.createToken(expiredClaims, new Date(System.currentTimeMillis() - 1000)); // 과거 시간

		// then
		assertThatThrownBy(() -> jwtProvider.getClaims(expiredToken))
			.isInstanceOf(ExpiredJwtException.class);
	}

}