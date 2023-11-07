package kr.codesquad.jazzmeet.image.mapper;

import java.time.LocalDateTime;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import kr.codesquad.jazzmeet.image.dto.response.ImageIdsResponse;
import kr.codesquad.jazzmeet.image.entity.Image;

@Mapper
public interface ImageMapper {

	ImageMapper INSTANCE = Mappers.getMapper(ImageMapper.class);

	Image toImage(String url, String status, LocalDateTime createdAt);

	ImageIdsResponse toImageIdsResponse(List<Long> ids);
}
