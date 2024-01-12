package kr.codesquad.jazzmeet.show.mapper;

import java.time.LocalDateTime;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import kr.codesquad.jazzmeet.image.entity.Image;
import kr.codesquad.jazzmeet.show.dto.request.RegisterShowRequest;
import kr.codesquad.jazzmeet.show.dto.response.ShowByDateAndVenueResponse;
import kr.codesquad.jazzmeet.show.dto.response.ShowDetailResponse;
import kr.codesquad.jazzmeet.show.dto.response.ShowResponse;
import kr.codesquad.jazzmeet.show.dto.response.UpcomingShowResponse;
import kr.codesquad.jazzmeet.show.entity.Show;
import kr.codesquad.jazzmeet.show.vo.ShowSummaryWithVenue;
import kr.codesquad.jazzmeet.venue.entity.Venue;

@Mapper
public interface ShowMapper {
	ShowMapper INSTANCE = Mappers.getMapper(ShowMapper.class);

	@Mapping(target = "venueId", source = "venue.id")
	@Mapping(target = "showId", source = "id")
	@Mapping(target = "posterUrl", source = "poster.url")
	UpcomingShowResponse toUpcomingShowResponse(Show show);

	@Mapping(target = "posterUrl", source = "poster.url")
	ShowByDateAndVenueResponse toShowByDateResponse(Show show);

	@Mapping(target = "totalCount", source = "totalElements")
	@Mapping(target = "maxPage", expression = "java(page.getTotalPages())")
	@Mapping(target = "currentPage", expression = "java(page.getNumber() + 1)")
	@Mapping(target = "shows", source = "content")
	ShowResponse toShowResponse(Page<ShowSummaryWithVenue> page);

	@Mapping(target = "venueName", source = "venue.name")
	ShowDetailResponse toShowDetailResponse(Show show);

	@Mapping(target = "description", source = "registerShowRequest.description")
	Show toShow(RegisterShowRequest registerShowRequest, Venue venue, Image poster);

	RegisterShowRequest toRegisterShowRequest(String teamName, String description, Long posterId,
		LocalDateTime startTime, LocalDateTime endTime);
}
