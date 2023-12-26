package kr.codesquad.jazzmeet.global.permission;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import kr.codesquad.jazzmeet.admin.entity.Admin;
import kr.codesquad.jazzmeet.admin.service.AdminService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AdminResolver implements HandlerMethodArgumentResolver {

	private final AdminService adminService;

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(AdminAuth.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

		Long adminId = Long.parseLong(String.valueOf(webRequest.getAttribute("adminId", WebRequest.SCOPE_REQUEST)));
		Admin admin = adminService.findAdminById(adminId);

		return admin;
	}
}
