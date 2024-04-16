package kr.codesquad.jazzmeet.global.jwt;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import kr.codesquad.jazzmeet.global.error.CustomException;
import kr.codesquad.jazzmeet.global.error.statuscode.ErrorCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class JwtProvider {

	private final JwtProperties jwtProperties;

	public Jwt createJwt(Map<String, Object> claims) {
		String accessToken = createToken(claims, getExpireDateAccessToken());
		String refreshToken = createToken(new HashMap<>(), getExpireDateRefreshToken());

		return Jwt.builder()
			.accessToken(accessToken)
			.refreshToken(refreshToken)
			.build();
	}

	public String createToken(Map<String, Object> claims, Date expiration) {
		return Jwts.builder()
			.setClaims(claims)
			.setExpiration(expiration)
			.signWith(SignatureAlgorithm.HS256,
				Base64.getEncoder().encodeToString(jwtProperties.getSecretKey().getBytes()))
			.compact();
	}

	private Date getExpireDateAccessToken() {
		return new Date(System.currentTimeMillis() + jwtProperties.getAccessTokenExpiration());
	}

	private Date getExpireDateRefreshToken() {
		return new Date(System.currentTimeMillis() + jwtProperties.getRefreshTokenExpiration());
	}

	public String reissueAccessToken(Map<String, Object> claims) {
		return createToken(claims, getExpireDateAccessToken());
	}

	public Claims validateAndGetClaims(String token) {
		try {
			return Jwts.parser()
				.setSigningKey(Base64.getEncoder().encodeToString(jwtProperties.getSecretKey().getBytes()))
				.parseClaimsJws(token)
				.getBody();
		} catch (JwtException e) {
			throw new CustomException(ErrorCode.from(e));
		}

	}

	public Long getExpireTimeRefreshToken() {
		return jwtProperties.getRefreshTokenExpiration() / 1000;
	}
}
