package kr.codesquad.jazzmeet.global.permission;

import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.codesquad.jazzmeet.admin.entity.UserRole;
import kr.codesquad.jazzmeet.global.error.CustomException;
import kr.codesquad.jazzmeet.global.error.statuscode.ErrorCode;
import kr.codesquad.jazzmeet.global.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PermissionInterceptor implements HandlerInterceptor {

	private final JwtProvider jwtProvider;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws
		Exception {

		// Preflight인 경우 통과
		if (CorsUtils.isPreFlightRequest(request)) {
			return true;
		}

		// HandlerMethod가 아니라면 통과
		if (!(handler instanceof HandlerMethod)) {
			return true;
		}

		// Permission 어노테이션 포함 여부 검사 로직
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		Permission permission = handlerMethod.getMethodAnnotation(Permission.class);
		if (permission == null) {
			return true;
		}

		String token = extractJwtTokenFromHeader(request);

		Claims claims = jwtProvider.validateAndGetClaims(token);
		String role = String.valueOf(claims.get("role"));
		String adminId = String.valueOf(claims.get("adminId"));

		// 일반 관리자인 경우
		if (role.equals(UserRole.ADMIN.name())) {
			request.setAttribute("adminId", adminId);
			return true;
		}

		// 루트 관리자인 경우
		if (role.equals(UserRole.ROOT_ADMIN.name())) {
			request.setAttribute("adminId", adminId);
			return true;
		}

		// 권한이 일치하지 않는 경우
		throw new CustomException(ErrorCode.UNAUTHORIZED_ACCESS);
	}

	private String extractJwtTokenFromHeader(HttpServletRequest request) {
		String authorization = request.getHeader("Authorization");
		if (authorization == null) {
			throw new CustomException(ErrorCode.TOKEN_MISSING);
		}

		try {
			return authorization.split(" ")[1];
		} catch (Exception e) {
			throw new CustomException(ErrorCode.INVALID_TOKEN_FORMAT);
		}
	}
}
