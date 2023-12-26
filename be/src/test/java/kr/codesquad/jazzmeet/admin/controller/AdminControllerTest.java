package kr.codesquad.jazzmeet.admin.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.codesquad.jazzmeet.admin.dto.request.LoginAdminRequest;
import kr.codesquad.jazzmeet.admin.dto.request.SignUpAdminRequest;
import kr.codesquad.jazzmeet.admin.service.AdminService;
import kr.codesquad.jazzmeet.global.jwt.Jwt;
import kr.codesquad.jazzmeet.global.jwt.JwtProperties;

@WebMvcTest(controllers = AdminController.class)
class AdminControllerTest {

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	MockMvc mockMvc;

	@MockBean
	AdminService adminService;

	@MockBean
	JwtProperties jwtProperties;

	@DisplayName("루트 관리자가 관리자 계정을 생성한다.")
	@Test
	void signUp() throws Exception {
		//given
		Long root = 1L;
		SignUpAdminRequest request = SignUpAdminRequest.builder()
			.loginId("admin1")
			.password("12345")
			.build();

		//when //then
		mockMvc.perform(
				post("/api/admins/sign-up")
					.requestAttr("id", root)
					.content(objectMapper.writeValueAsString(request))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isCreated());
	}

	@DisplayName("루트 관리자가 관리자 계정을 생성할 때 loginId는 5자 이상이어야 한다.")
	@Test
	void signUpWhenLoginIdLessThenFive() throws Exception {
		//given
		Long root = 1L;
		String target = "admi";
		SignUpAdminRequest request = SignUpAdminRequest.builder()
			.loginId(target)
			.password("12345")
			.build();

		//when //then
		mockMvc.perform(
				post("/api/admins/sign-up")
					.requestAttr("id", root)
					.content(objectMapper.writeValueAsString(request))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isBadRequest());
	}

	@DisplayName("루트 관리자가 관리자 계정을 생성할 때 loginId는 20자 이하여야 한다.")
	@Test
	void signUpWhenLoginIdGraterThenTwenty() throws Exception {
		//given
		Long root = 1L;
		String target = "a".repeat(21);
		SignUpAdminRequest request = SignUpAdminRequest.builder()
			.loginId(target)
			.password("12345")
			.build();

		//when //then
		mockMvc.perform(
				post("/api/admins/sign-up")
					.requestAttr("id", root)
					.content(objectMapper.writeValueAsString(request))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isBadRequest());
	}

	@DisplayName("루트 관리자가 관리자 계정을 생성할 때 password는 5자 이상이어야 한다.")
	@Test
	void signUpWhenPasswordLessThenFive() throws Exception {
		//given
		Long root = 1L;
		String target = "1234";
		SignUpAdminRequest request = SignUpAdminRequest.builder()
			.loginId("root1")
			.password(target)
			.build();

		//when //then
		mockMvc.perform(
				post("/api/admins/sign-up")
					.requestAttr("id", root)
					.content(objectMapper.writeValueAsString(request))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isBadRequest());
	}

	@DisplayName("루트 관리자가 관리자 계정을 생성할 때 password는 20자 이하여야 한다.")
	@Test
	void signUpWhenPasswordGraterThenTwenty() throws Exception {
		//given
		Long root = 1L;
		String target = "1".repeat(21);
		SignUpAdminRequest request = SignUpAdminRequest.builder()
			.loginId("root1")
			.password(target)
			.build();

		//when //then
		mockMvc.perform(
				post("/api/admins/sign-up")
					.requestAttr("id", root)
					.content(objectMapper.writeValueAsString(request))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isBadRequest());
	}

	@DisplayName("루트 관리자가 관리자 계정을 생성할 때 loginId는 null이면 안 된다.")
	@Test
	void signUpWhenLoginIdIsNull() throws Exception {
		//given
		Long root = 1L;
		String target = null;
		SignUpAdminRequest request = SignUpAdminRequest.builder()
			.loginId(target)
			.password("12345")
			.build();

		//when //then
		mockMvc.perform(
				post("/api/admins/sign-up")
					.requestAttr("id", root)
					.content(objectMapper.writeValueAsString(request))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isBadRequest());
	}

	@DisplayName("루트 관리자가 관리자 계정을 생성할 때 password는 null이면 안 된다.")
	@Test
	void signUpWhenPasswordIsNull() throws Exception {
		//given
		Long root = 1L;
		String target = null;
		SignUpAdminRequest request = SignUpAdminRequest.builder()
			.loginId("admin1")
			.password(target)
			.build();

		//when //then
		mockMvc.perform(
				post("/api/admins/sign-up")
					.requestAttr("id", root)
					.content(objectMapper.writeValueAsString(request))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isBadRequest());
	}

	@DisplayName("관리자 계정으로 로그인을 한다.")
	@Test
	void loginAdmin() throws Exception {
	    //given
		LoginAdminRequest request = LoginAdminRequest.builder()
			.loginId("admin1")
			.password("12345")
			.build();

		when(adminService.login(request)).thenReturn(Jwt.builder()
				.accessToken("accessToken")
				.refreshToken("refreshToken")
				.build());


		//when //then
		mockMvc.perform(
				post("/api/admins/login")
					.content(objectMapper.writeValueAsString(request))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(cookie().httpOnly("refreshToken", true))
			.andExpect(cookie().value("refreshToken", "refreshToken"))
			.andExpect(jsonPath("$.accessToken").value("accessToken"));
	}

	@DisplayName("관리자 계정으로 로그인할 때 아이디는 5자 이상 , 20자 이하여야 한다.")
	@Test
	void failLoginAdmin_wrongIdSize() throws Exception {
	    //given
		LoginAdminRequest request = LoginAdminRequest.builder()
			.loginId("admi")
			.password("12345")
			.build();

	    //when //then
		mockMvc.perform(
				post("/api/admins/login")
					.content(objectMapper.writeValueAsString(request))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isBadRequest());
	}

	@DisplayName("관리자 계정으로 로그인할 때 비밀번호는 5자 이상 , 20자 이하여야 한다.")
	@Test
	void failLoginAdmin_wrongPasswordSize() throws Exception {
	    //given
		LoginAdminRequest request = LoginAdminRequest.builder()
			.loginId("admin")
			.password("1234")
			.build();

	    //when //then
		mockMvc.perform(
				post("/api/admins/login")
					.content(objectMapper.writeValueAsString(request))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isBadRequest());
	}
}