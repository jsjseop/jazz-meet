package kr.codesquad.jazzmeet.image.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import kr.codesquad.jazzmeet.image.entity.Image;
import kr.codesquad.jazzmeet.image.entity.ImageStatus;

@Mapper
public interface ImageMapper {

	ImageMapper INSTANCE = Mappers.getMapper(ImageMapper.class);

	Image toImage(String url, ImageStatus status);
}
