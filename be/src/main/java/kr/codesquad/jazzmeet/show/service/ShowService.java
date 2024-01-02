package kr.codesquad.jazzmeet.show.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.codesquad.jazzmeet.global.error.CustomException;
import kr.codesquad.jazzmeet.global.error.statuscode.ShowErrorCode;
import kr.codesquad.jazzmeet.image.entity.Image;
import kr.codesquad.jazzmeet.image.service.ImageService;
import kr.codesquad.jazzmeet.show.dto.request.RegisterShowRequest;
import kr.codesquad.jazzmeet.show.dto.response.ExistShowCalendarResponse;
import kr.codesquad.jazzmeet.show.dto.response.RegisterShowResponse;
import kr.codesquad.jazzmeet.show.dto.response.ShowByDateAndVenueResponse;
import kr.codesquad.jazzmeet.show.dto.response.ShowByDateResponse;
import kr.codesquad.jazzmeet.show.dto.response.ShowDetailResponse;
import kr.codesquad.jazzmeet.show.dto.response.ShowResponse;
import kr.codesquad.jazzmeet.show.dto.response.UpcomingShowResponse;
import kr.codesquad.jazzmeet.show.entity.Show;
import kr.codesquad.jazzmeet.show.mapper.ShowMapper;
import kr.codesquad.jazzmeet.show.repository.ShowQueryRepository;
import kr.codesquad.jazzmeet.show.repository.ShowRepository;
import kr.codesquad.jazzmeet.show.vo.ShowSummaryWithVenue;
import kr.codesquad.jazzmeet.show.vo.ShowWithVenue;
import kr.codesquad.jazzmeet.venue.entity.Venue;
import kr.codesquad.jazzmeet.venue.service.VenueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ShowService {

	private static final int PAGE_NUMBER_OFFSET = 1;
	private static final int PAGE_SIZE = 10;

	private final ShowRepository showRepository;
	private final ShowQueryRepository showQueryRepository;
	private final VenueService venueService;
	private final ImageService imageService;

	private static final String FIRST_DAY_OF_MONTH = "01";

	public List<UpcomingShowResponse> getUpcomingShows(LocalDateTime nowTime) {
		// 10개 제한, 현재 시간 < 공연 시작 시간 , 현재 시간 < 공연 끝나는 시간, 공연 시작 시간 순으로 오름차순 정렬
		// TODO: N+1 문제 해결 필요
		List<Show> shows = showRepository.findTop10BystartTimeGreaterThanOrEndTimeGreaterThanOrderByStartTime(
			nowTime, nowTime);

		return shows.stream().map(ShowMapper.INSTANCE::toUpcomingShowResponse).toList();
	}

	public List<ShowByDateAndVenueResponse> getShowsByDate(Long venueId, String date) {
		if (isDate(date)) {
			return List.of();
		}

		List<Show> shows = showRepository.findByVenueIdAndDate(venueId, getLocalDate(date));

		return shows.stream()
			.map(ShowMapper.INSTANCE::toShowByDateResponse)
			.toList();
	}

	private LocalDate getLocalDate(String date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		try {
			LocalDate formattedDate = LocalDate.parse(date, formatter);
			return formattedDate;
		} catch (DateTimeParseException e) {
			throw new CustomException(ShowErrorCode.NOT_VALID_DATE_FORMAT);
		}
	}

	private boolean isDate(String date) {
		return date == null;
	}

	public ExistShowCalendarResponse getExistShows(Long venueId, String date) {
		LocalDate localDate = getLocalDate(date + FIRST_DAY_OF_MONTH);
		List<Integer> existShowsByMonth = showQueryRepository.getExistShowsByMonth(venueId, localDate);

		return new ExistShowCalendarResponse(existShowsByMonth);
	}

	public ExistShowCalendarResponse getShowCalendar(String date) {
		LocalDate localDate = getLocalDate(date + FIRST_DAY_OF_MONTH);
		List<Integer> showCalendar = showQueryRepository.getShowCalendar(localDate);

		return new ExistShowCalendarResponse(showCalendar);
	}

	public List<ShowByDateResponse> getShowsByDate(String date) {
		LocalDate localDate = getLocalDate(date);
		List<ShowWithVenue> showsWithVenue = showQueryRepository.getShowsByDate(localDate);

		return showsWithVenue.stream()
			.sorted(Comparator.comparing(ShowWithVenue::getCityAndDistrict))
			.collect(Collectors.groupingBy(ShowWithVenue::getCityAndDistrict,
				LinkedHashMap::new, Collectors.toList()))
			.entrySet()
			.stream()
			.map(response -> new ShowByDateResponse(response.getKey(), response.getValue()))
			.toList();
	}

	public ShowResponse getShows(String word, int page) {
		PageRequest pageRequest = PageRequest.of(page - PAGE_NUMBER_OFFSET, PAGE_SIZE);
		Page<ShowSummaryWithVenue> shows = showQueryRepository.getShows(word, pageRequest);

		return ShowMapper.INSTANCE.toShowResponse(shows);
	}

	public ShowDetailResponse getShowDetail(Long showId) {
		Show show = getShowById(showId);

		return ShowMapper.INSTANCE.toShowDetailResponse(show);
	}

	private Show getShowById(Long showId) {
		return showRepository.findEntireShowById(showId)
			.orElseThrow(() -> new CustomException(ShowErrorCode.NOT_FOUND_SHOW));
	}

	@Transactional
	public RegisterShowResponse registerShow(Long venueId, RegisterShowRequest registerShowRequest) {
		Venue venue = venueService.findById(venueId);
		Long posterId = registerShowRequest.posterId();
		Image poster = null;
		if (posterId != null) {
			poster = imageService.findById(posterId);
		}
		Show show = ShowMapper.INSTANCE.toShow(registerShowRequest, venue, poster);

		Show savedShow = showRepository.save(show);

		return new RegisterShowResponse(savedShow.getId());
	}

	@Transactional
	public ShowDetailResponse updateShow(Long showId, RegisterShowRequest request) {
		Show show = getShowById(showId);
		Image poster = imageService.findById(request.posterId());

		show.updateShow(request.teamName(), request.description(), request.startTime(),
			request.endTime(), poster);

		return ShowMapper.INSTANCE.toShowDetailResponse(show);
	}

	@Transactional
	public void deleteShow(Long showId) {
		Show show = getShowById(showId);

		show.deletePoster();
		showRepository.delete(show);
	}

	public Optional<Show> getLatestShowBy(Long venueId) {
		return showRepository.findLatestShowByVenueId(venueId);
	}
}