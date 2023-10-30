package kr.codesquad.jazzmeet.show.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import kr.codesquad.jazzmeet.show.dto.response.UpcomingShowResponse;
import kr.codesquad.jazzmeet.show.entity.Show;

@Mapper
public interface ShowMapper {
	ShowMapper INSTANCE = Mappers.getMapper(ShowMapper.class);

	@Mapping(target = "venueId", source = "venue.id")
	@Mapping(target = "showId", source = "id")
	@Mapping(target = "posterUrl", source = "poster.url")
	@Mapping(target = "showName", source = "teamName")
	UpcomingShowResponse toUpcomingShowResponse(Show show);
}
