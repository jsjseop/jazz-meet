package kr.codesquad.jazzmeet.image.mapper;

import java.time.LocalDateTime;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import kr.codesquad.jazzmeet.image.entity.Image;

@Mapper
public interface ImageMapper {

	ImageMapper INSTANCE = Mappers.getMapper(ImageMapper.class);

	Image toImage(String url, String status, LocalDateTime createdAt);
}
