package kr.codesquad.jazzmeet.show.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import kr.codesquad.jazzmeet.show.dto.request.RegisterShowRequest;
import kr.codesquad.jazzmeet.show.repository.OcrHandler;
import kr.codesquad.jazzmeet.show.repository.WebCrawler;
import kr.codesquad.jazzmeet.venue.entity.Venue;
import kr.codesquad.jazzmeet.venue.service.VenueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@Component
public class ShowScheduler {
	public static final String ENTRY55 = "entry55";
	public static final String ENTRY55_INSTAGRAM_URL = "https://www.instagram.com/entry55_official/";

	private final ShowService showService;
	private final VenueService venueService;
	private final OcrHandler ocrHandler;
	private final WebCrawler crawler;

	@Transactional
	public void autoInsertShowSchedule() {
		// 	// TODO: 모든 공연장에 대한 인스타그램 링크를 가져온다.
		// 	List<Link> venueLinks = venueService.findAllByLinkTypeEqualsInstagram();
		// 	for (Link link : venueLinks) {
		// 	}
		Venue venue = venueService.findByName(ENTRY55);
		String venueInstagramUrl = ENTRY55_INSTAGRAM_URL;

		// 공연장에 저장 된 최신 공연을 가져온다.
		LocalDate latestShowDate = showService.getLatestShowBy(venue.getId())
			.map(show -> show.getStartTime().toLocalDate())
			.orElse(null);

		// 1. 인스타그램에서 (공연 스케줄이 담겨있는) 이미지 url을 크롤링 해 온다.
		List<String> showImageUrls = crawler.getShowImageUrls(venueInstagramUrl, latestShowDate);

		for (String showImageUrl : showImageUrls) {
			// 2. 네이버 ocr에 텍스트 추출 요청을 보내고 response를 파싱해 공연 등록 request로 만든다.
			List<RegisterShowRequest> requests = ocrHandler.getShows(venue.getName(), showImageUrl, latestShowDate);
			// 3. DB에 새로운 공연을 넣는다.
			for (RegisterShowRequest request : requests) {
				showService.registerShow(venue.getId(), request);
			}
		}
	}

}
