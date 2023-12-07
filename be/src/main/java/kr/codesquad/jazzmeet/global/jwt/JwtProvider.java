package kr.codesquad.jazzmeet.global.jwt;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
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

	public String reissueAccessToken(Map<String, Object> claims) {
		return createToken(claims, getExpireDateAccessToken());
	}

	public String createToken(Map<String, Object> claims, Date expiration) {
		return Jwts.builder()
			.setClaims(claims)
			.setExpiration(expiration)
			.signWith(SignatureAlgorithm.HS256,
				Base64.getEncoder().encodeToString(jwtProperties.getSecretKey().getBytes()))
			.compact();
	}

	public Date getExpireDateAccessToken() {
		return new Date(System.currentTimeMillis() + jwtProperties.getAccessTokenExpiration());
	}

	public Date getExpireDateRefreshToken() {
		return new Date(System.currentTimeMillis() + jwtProperties.getRefreshTokenExpiration());
	}

	public Claims getClaims(String token) {
		return Jwts.parser()
			.setSigningKey(Base64.getEncoder().encodeToString(jwtProperties.getSecretKey().getBytes()))
			.parseClaimsJws(token)
			.getBody();
	}
}
