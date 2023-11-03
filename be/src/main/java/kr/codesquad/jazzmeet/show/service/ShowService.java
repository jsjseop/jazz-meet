package kr.codesquad.jazzmeet.show.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.codesquad.jazzmeet.global.error.CustomException;
import kr.codesquad.jazzmeet.global.error.statuscode.ErrorCode;
import kr.codesquad.jazzmeet.show.dto.response.ExistShowCalendarResponse;
import kr.codesquad.jazzmeet.show.dto.response.ShowByDateResponse;
import kr.codesquad.jazzmeet.show.dto.response.UpcomingShowResponse;
import kr.codesquad.jazzmeet.show.entity.Show;
import kr.codesquad.jazzmeet.show.mapper.ShowMapper;
import kr.codesquad.jazzmeet.show.repository.ShowQueryRepository;
import kr.codesquad.jazzmeet.show.repository.ShowRepository;
import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ShowService {

	private final ShowRepository showRepository;
	private final ShowQueryRepository showQueryRepository;

	private static final String FIRST_DAY_OF_MONTH = "01";

	public List<UpcomingShowResponse> getUpcomingShows(LocalDateTime nowTime) {
		// 10개 제한, 현재 시간 < 공연 시작 시간 , 현재 시간 < 공연 끝나는 시간, 공연 시작 시간 순으로 오름차순 정렬
		// TODO: N+1 문제 해결 필요
		List<Show> shows = showRepository.findTop10BystartTimeGreaterThanOrEndTimeGreaterThanOrderByStartTime(
			nowTime, nowTime);

		return shows.stream().map(ShowMapper.INSTANCE::toUpcomingShowResponse).toList();
	}

	public List<ShowByDateResponse> getShows(Long venueId, String date) {
		if (isDate(date)) {
			return List.of();
		}

		List<Show> shows = showRepository.findByVenueIdAndDate(venueId, getLocalDate(date));

		return shows.stream()
			.map(ShowMapper.INSTANCE::toShowByDateResponse)
			.toList();
	}

	private static LocalDate getLocalDate(String date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		try {
			LocalDate formattedDate = LocalDate.parse(date, formatter);
			return formattedDate;
		} catch (DateTimeParseException e) {
			throw new CustomException(ErrorCode.NOT_VALID_DATE_FORMAT);
		}
	}

	private static boolean isDate(String date) {
		return date == null;
	}

	public ExistShowCalendarResponse getExistShows(Long venueId, String date) {
		LocalDate localDate = getLocalDate(date + FIRST_DAY_OF_MONTH);
		List<Integer> existShowsByMonth = showQueryRepository.getExistShowsByMonth(venueId, localDate);

		return new ExistShowCalendarResponse(existShowsByMonth);
	}
}