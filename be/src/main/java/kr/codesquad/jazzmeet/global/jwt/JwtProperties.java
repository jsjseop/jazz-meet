package kr.codesquad.jazzmeet.global.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

	private final String secretKey;
	private final Long accessTokenExpiration;
	private final Long refreshTokenExpiration;

}
