package kr.codesquad.jazzmeet.venue.service;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import kr.codesquad.jazzmeet.IntegrationTestSupport;
import kr.codesquad.jazzmeet.fixture.ImageFixture;
import kr.codesquad.jazzmeet.global.error.CustomException;
import kr.codesquad.jazzmeet.image.entity.Image;
import kr.codesquad.jazzmeet.image.repository.ImageRepository;
import kr.codesquad.jazzmeet.venue.dto.request.VenueCreateHour;
import kr.codesquad.jazzmeet.venue.dto.request.VenueCreateLink;
import kr.codesquad.jazzmeet.venue.dto.request.VenueCreateRequest;
import kr.codesquad.jazzmeet.venue.dto.response.VenueCreateResponse;
import kr.codesquad.jazzmeet.venue.entity.LinkType;
import kr.codesquad.jazzmeet.venue.repository.LinkTypeRepository;

@Transactional
class VenueFacadeTest extends IntegrationTestSupport {

	@Autowired
	VenueFacade venueFacade;

	@Autowired
	ImageRepository imageRepository;

	@Autowired
	LinkTypeRepository linkTypeRepository;

	@Test
	@DisplayName("공연장 데이터를 입력 받아서 공연장을 생성한다")
	void createVenue() {
	    // given
		Image image1 = ImageFixture.createImage("url1");
		Image image2 = ImageFixture.createImage("url2");
		List<Image> images = imageRepository.saveAll(List.of(image1, image2));

		LinkType naverMapLinkType = new LinkType("naverMap");
		LinkType instagramLinkType = new LinkType("instagram");
		linkTypeRepository.saveAll(List.of(naverMapLinkType, instagramLinkType));

		VenueCreateLink link1 = new VenueCreateLink("naverMap", "www.naverMap.com");
		VenueCreateLink link2 = new VenueCreateLink("instagram", "www.instagram.com");

		VenueCreateHour venueHour1 = new VenueCreateHour("월요일", "휴무");
		VenueCreateHour venueHour2 = new VenueCreateHour("화요일", "휴무");
		VenueCreateHour venueHour3 = new VenueCreateHour("수요일", "휴무");

		VenueCreateRequest venueCreateRequest = VenueCreateRequest.builder()
			.name("공연장")
			.imageIds(List.of(images.get(0).getId(), images.get(1).getId()))
			.roadNameAddress("도로명 주소")
			.lotNumberAddress("지번 주소")
			.phoneNumber("010-1234-5678")
			.description("공연장 설명")
			.links(List.of(link1, link2))
			.venueHours(List.of(venueHour1, venueHour2, venueHour3))
			.latitude(37.50049856339995)
			.longitude(127.0249505634053)
			.build();

		// when
		VenueCreateResponse response = venueFacade.createVenue(venueCreateRequest);

		// then
		assertThat(response).extracting("id").isNotNull();
	}

	@Test
	@DisplayName("이미지 개수가 10개가 넘으면 공연장을 생성할 수 없다")
	void createVenueWithManyImage() {
		// given
		VenueCreateRequest venueCreateRequest = VenueCreateRequest.builder()
			.name("공연장")
			.imageIds(List.of(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L, 11L))
			.roadNameAddress("도로명 주소")
			.lotNumberAddress("지번 주소")
			.phoneNumber("010-1234-5678")
			.description("공연장 설명")
			.latitude(37.50049856339995)
			.longitude(127.0249505634053)
			.build();

		// when then
		assertThatThrownBy(() -> venueFacade.createVenue(venueCreateRequest))
			.isInstanceOf(CustomException.class);
	}
}
