package kr.codesquad.jazzmeet.show.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import kr.codesquad.jazzmeet.show.dto.response.UpcomingShowResponse;
import kr.codesquad.jazzmeet.show.entity.Show;
import kr.codesquad.jazzmeet.show.mapper.ShowMapper;
import kr.codesquad.jazzmeet.show.repository.ShowRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ShowService {

	private final ShowRepository showRepository;

	public List<UpcomingShowResponse> getUpcomingShows(LocalDateTime nowTime) {
		// 10개 제한, 현재 시간 < 공연 시작 시간 , 현재 시간 < 공연 끝나는 시간, 공연 시작 시간 순으로 오름차순 정렬
		// TODO: N+1 문제 해결 필요
		List<Show> shows = showRepository.findTop10BystartTimeGreaterThanOrEndTimeGreaterThanOrderByStartTime(
			nowTime, nowTime);

		return shows.stream().map(ShowMapper.INSTANCE::toUpcomingShowResponse).toList();
	}
}