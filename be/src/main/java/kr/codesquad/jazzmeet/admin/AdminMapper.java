package kr.codesquad.jazzmeet.admin;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import kr.codesquad.jazzmeet.admin.dto.request.SignUpAdminRequest;
import kr.codesquad.jazzmeet.admin.entity.Admin;
import kr.codesquad.jazzmeet.admin.entity.UserRole;

@Mapper
public interface AdminMapper {
	AdminMapper INSTANCE = Mappers.getMapper(AdminMapper.class);

	@Mapping(target = "loginId", source = "signUpAdminRequest.loginId")
	@Mapping(target = "password", source = "encodedPassword")
	Admin toAdmin(SignUpAdminRequest signUpAdminRequest, String encodedPassword, UserRole role);
}
