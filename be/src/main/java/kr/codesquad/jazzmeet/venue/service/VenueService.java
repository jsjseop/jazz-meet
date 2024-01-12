package kr.codesquad.jazzmeet.venue.service;

import java.time.LocalDate;
import java.util.List;

import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.codesquad.jazzmeet.global.error.CustomException;
import kr.codesquad.jazzmeet.global.error.statuscode.VenueErrorCode;
import kr.codesquad.jazzmeet.venue.dto.VenueInfo;
import kr.codesquad.jazzmeet.venue.dto.request.RangeCoordinatesRequest;
import kr.codesquad.jazzmeet.venue.dto.response.NearbyVenueResponse;
import kr.codesquad.jazzmeet.venue.dto.response.VenueAutocompleteResponse;
import kr.codesquad.jazzmeet.venue.dto.response.VenueDetailResponse;
import kr.codesquad.jazzmeet.venue.dto.response.VenueListResponse;
import kr.codesquad.jazzmeet.venue.dto.response.VenuePinsResponse;
import kr.codesquad.jazzmeet.venue.dto.response.VenueSearchResponse;
import kr.codesquad.jazzmeet.venue.entity.Link;
import kr.codesquad.jazzmeet.venue.entity.LinkType;
import kr.codesquad.jazzmeet.venue.entity.Venue;
import kr.codesquad.jazzmeet.venue.mapper.VenueMapper;
import kr.codesquad.jazzmeet.venue.repository.LinkRepository;
import kr.codesquad.jazzmeet.venue.repository.LinkTypeRepository;
import kr.codesquad.jazzmeet.venue.repository.VenueQueryRepository;
import kr.codesquad.jazzmeet.venue.repository.VenueRepository;
import kr.codesquad.jazzmeet.venue.util.LocationUtil;
import kr.codesquad.jazzmeet.venue.vo.NearbyVenue;
import kr.codesquad.jazzmeet.venue.vo.VenueDetail;
import kr.codesquad.jazzmeet.venue.vo.VenuePins;
import kr.codesquad.jazzmeet.venue.vo.VenueSearchData;
import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class VenueService {
	private static final int PAGE_NUMBER_OFFSET = 1; // 페이지를 1부터 시작하게 하기 위한 offset
	private static final int PAGE_SIZE = 10;
	private static final int ADMIN_PAGE_SIZE = 20;
	public static final String LINK_TYPE_NAME_INSTAGRAM = "instagram";

	private final VenueRepository venueRepository;
	private final VenueQueryRepository venueQueryRepository;
	private final LinkRepository linkRepository;
	private final LinkTypeRepository linkTypeRepository;

	public List<VenueAutocompleteResponse> searchAutocompleteList(String word) {
		// word가 "" 이면 공연장 목록 전부 조회
		List<Venue> venues = venueRepository.findTop10ByNameContainingOrRoadNameAddressContaining(word,
			word);

		return venues.stream()
			.map(VenueMapper.INSTANCE::toVenueAutocompleteResponse)
			.toList();
	}

	public List<NearbyVenueResponse> findNearByVenues(Double latitude, Double longitude) {
		if (LocationUtil.hasNull(latitude, longitude)) {
			return List.of();
		}

		Point point = LocationUtil.createPoint(latitude, longitude);
		List<NearbyVenue> venues = venueQueryRepository.findNearbyVenuesByLocation(point);

		return venues.stream()
			.map(VenueMapper.INSTANCE::toNearByVenueResponse)
			.toList();
	}

	public List<VenuePinsResponse> findVenuePinsBySearch(String word) {
		if (word == null) {
			return List.of();
		}

		List<VenuePins> venues = venueQueryRepository.findVenuePinsByWord(word);

		return venues.stream()
			.map(VenueMapper.INSTANCE::toVenuePinsBySearchResponse)
			.toList();
	}

	public List<VenuePinsResponse> findVenuePinsByLocation(RangeCoordinatesRequest rangeCoordinatesRequest) {
		if (rangeCoordinatesRequest.hasNull()) {
			return List.of();
		}

		Polygon range = rangeCoordinatesRequest.toRange();
		List<VenuePins> venues = venueQueryRepository.findVenuePinsByLocation(range);

		return venues.stream()
			.map(VenueMapper.INSTANCE::toVenuePinsBySearchResponse)
			.toList();
	}

	public VenueSearchResponse findVenuesByLocation(RangeCoordinatesRequest rangeCoordinatesRequest, int page) {
		if (rangeCoordinatesRequest.hasNull()) {
			return VenueSearchResponse.emptyVenues();
		}

		Polygon range = rangeCoordinatesRequest.toRange();
		PageRequest pageRequest = PageRequest.of(page - PAGE_NUMBER_OFFSET, PAGE_SIZE);
		LocalDate curDate = LocalDate.now();
		Page<VenueSearchData> venuesByLocation = venueQueryRepository.findVenuesByLocation(range, pageRequest, curDate);

		return VenueMapper.INSTANCE.toVenueSearchResponse(venuesByLocation,
			venuesByLocation.getNumber() + PAGE_NUMBER_OFFSET);
	}

	public Venue findById(Long venueId) {
		return venueRepository.findById(venueId)
			.orElseThrow(() -> new CustomException(VenueErrorCode.NOT_FOUND_VENUE));
	}

	public VenueDetailResponse findVenue(Long venueId) {
		VenueDetail venueDetail = venueQueryRepository.findVenue(venueId)
			.orElseThrow(() -> new CustomException(VenueErrorCode.NOT_FOUND_VENUE));

		return VenueMapper.INSTANCE.toVenueDetailResponse(venueDetail);
	}

	public VenueSearchResponse searchVenueList(String word, int page) {
		if (word == "" || word == null) {
			return VenueSearchResponse.emptyVenues();
		}
		PageRequest pageRequest = PageRequest.of(page - PAGE_NUMBER_OFFSET, PAGE_SIZE);
		LocalDate curDate = LocalDate.now();

		Page<VenueSearchData> venueSearchDataList = venueQueryRepository
			.searchVenueList(word, pageRequest, curDate);

		return VenueMapper.INSTANCE.toVenueSearchResponse(venueSearchDataList,
			venueSearchDataList.getNumber() + PAGE_NUMBER_OFFSET);
	}

	public VenueSearchResponse findVenueSearchById(Long venueId) {
		LocalDate curDate = LocalDate.now();
		List<VenueSearchData> venueSearchDataList = venueQueryRepository.findVenueSearchById(venueId, curDate);

		return VenueMapper.INSTANCE.toVenueSearchResponse(venueSearchDataList, venueSearchDataList.size(),
			PAGE_NUMBER_OFFSET, PAGE_NUMBER_OFFSET);
	}

	public VenueListResponse findVenuesByWord(String word, int page) {
		PageRequest pageRequest = PageRequest.of(page - PAGE_NUMBER_OFFSET, ADMIN_PAGE_SIZE);

		Page<VenueInfo> venueInfos = venueQueryRepository.findVenuesByWord(word, pageRequest);

		return VenueMapper.INSTANCE.toVenueListResponse(venueInfos, venueInfos.getNumber() + PAGE_NUMBER_OFFSET);
	}

	@Transactional
	public Venue save(Venue venue) {
		return venueRepository.save(venue);
	}

	@Transactional
	public void deleteById(Long venueId) {
		venueRepository.deleteById(venueId);
	}

	public List<Link> findAllByLinkTypeEqualsInstagram() {
		LinkType linkType = linkTypeRepository.findByName(LINK_TYPE_NAME_INSTAGRAM)
			.orElseThrow(() -> new CustomException(VenueErrorCode.NOT_FOUND_LINK_TYPE));
		return linkRepository.findByVenueIsNotNullAndLinkType(linkType);
	}

	public Venue findByName(String venueName) {
		return venueRepository.findByName(venueName)
			.orElseThrow(() -> new CustomException(VenueErrorCode.NOT_FOUND_VENUE));
	}
}
