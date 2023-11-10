package kr.codesquad.jazzmeet.venue.service;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import kr.codesquad.jazzmeet.IntegrationTestSupport;
import kr.codesquad.jazzmeet.fixture.ImageFixture;
import kr.codesquad.jazzmeet.fixture.VenueFixture;
import kr.codesquad.jazzmeet.image.entity.Image;
import kr.codesquad.jazzmeet.image.entity.ImageStatus;
import kr.codesquad.jazzmeet.image.repository.ImageRepository;
import kr.codesquad.jazzmeet.venue.dto.request.VenueCreateRequest;
import kr.codesquad.jazzmeet.venue.dto.request.VenueHourRequest;
import kr.codesquad.jazzmeet.venue.dto.request.VenueLinkRequest;
import kr.codesquad.jazzmeet.venue.dto.request.VenueUpdateRequest;
import kr.codesquad.jazzmeet.venue.dto.response.VenueCreateResponse;
import kr.codesquad.jazzmeet.venue.dto.response.VenueDetailResponse;
import kr.codesquad.jazzmeet.venue.entity.LinkType;
import kr.codesquad.jazzmeet.venue.entity.Venue;
import kr.codesquad.jazzmeet.venue.entity.VenueImage;
import kr.codesquad.jazzmeet.venue.repository.LinkTypeRepository;
import kr.codesquad.jazzmeet.venue.repository.VenueRepository;

class VenueFacadeTest extends IntegrationTestSupport {

	@Autowired
	VenueFacade venueFacade;

	@Autowired
	ImageRepository imageRepository;

	@Autowired
	LinkTypeRepository linkTypeRepository;

	@Autowired
	VenueRepository venueRepository;

	@AfterEach
	void dbClean() {
		imageRepository.deleteAllInBatch();
		linkTypeRepository.deleteAllInBatch();
	}

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

		VenueLinkRequest link1 = new VenueLinkRequest("naverMap", "www.naverMap.com");
		VenueLinkRequest link2 = new VenueLinkRequest("instagram", "www.instagram.com");

		VenueHourRequest venueHour1 = new VenueHourRequest("월요일", "휴무");
		VenueHourRequest venueHour2 = new VenueHourRequest("화요일", "휴무");
		VenueHourRequest venueHour3 = new VenueHourRequest("수요일", "휴무");

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

		venueRepository.deleteById(response.id());
	}

	@Test
	@DisplayName("입력 받은 데이터로 공연장 정보를 수정한다")
	void updateVenue() {
		// given
		Image image1 = ImageFixture.createImage("url1");
		Image image2 = ImageFixture.createImage("url2");
		List<Image> images = imageRepository.saveAll(List.of(image1, image2));

		LinkType naverMapLinkType = new LinkType("naverMap");
		LinkType instagramLinkType = new LinkType("instagram");
		linkTypeRepository.saveAll(List.of(naverMapLinkType, instagramLinkType));

		VenueLinkRequest link1 = new VenueLinkRequest("naverMap", "www.naverMap.com");
		VenueLinkRequest link2 = new VenueLinkRequest("instagram", "www.instagram.com");

		VenueHourRequest venueHour1 = new VenueHourRequest("월요일", "휴무");
		VenueHourRequest venueHour2 = new VenueHourRequest("화요일", "휴무");
		VenueHourRequest venueHour3 = new VenueHourRequest("수요일", "휴무");

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

		// 공연장 생성
		Long venueId = venueFacade.createVenue(venueCreateRequest).id();

		Image image3 = ImageFixture.createImage("url3");
		Image image4 = ImageFixture.createImage("url4");
		List<Image> updateImages = imageRepository.saveAll(List.of(image3, image4));

		LinkType officialLinkType = new LinkType("official");
		LinkType etcLinkType = new LinkType("etc");
		linkTypeRepository.saveAll(List.of(officialLinkType, etcLinkType));

		VenueLinkRequest link3 = new VenueLinkRequest("official", "www.official.com");
		VenueLinkRequest link4 = new VenueLinkRequest("etc", "www.etc.com");

		VenueHourRequest venueHour4 = new VenueHourRequest("목요일", "휴무");
		VenueHourRequest venueHour5 = new VenueHourRequest("금요일", "휴무");
		VenueHourRequest venueHour6 = new VenueHourRequest("토요일", "휴무");

		// 수정 데이터
		VenueUpdateRequest venueupdateRequest = VenueUpdateRequest.builder()
			.name("공연장2")
			.imageIds(List.of(updateImages.get(0).getId(), updateImages.get(1).getId()))
			.roadNameAddress("도로명 주소2")
			.lotNumberAddress("지번 주소2")
			.phoneNumber("010-2222-2222")
			.description("공연장 설명2")
			.links(List.of(link3, link4))
			.venueHours(List.of(venueHour4, venueHour5, venueHour6))
			.latitude(38.50049856339995)
			.longitude(128.0249505634053)
			.build();

		// when
		VenueDetailResponse response = venueFacade.updateVenue(venueupdateRequest, venueId);

		// then
		Assertions.assertAll(
			() -> assertThat(response)
				.extracting("name", "roadNameAddress", "lotNumberAddress")
				.containsExactly("공연장2", "도로명 주소2", "지번 주소2"),
			() -> assertThat(response.images())
				.extracting("url")
				.containsExactly(image3.getUrl(), image4.getUrl()),
			() -> assertThat(response.links())
				.extracting("type")
				.containsExactly(link3.type(), link4.type()),
			() -> assertThat(response.venueHours())
			.extracting("day")
			.containsExactly(venueHour4.day(), venueHour5.day(), venueHour6.day())
		);

		venueRepository.deleteById(response.id());
	}

	@Test
	@DisplayName("공연장을 삭제하면 이미지의 상태가 삭제 상태로 변경된다")
	void deleteVenue() {
	    // given
		Venue venue = VenueFixture.createVenue("공연장", "공연장 주소");

		Image image1 = ImageFixture.createImage("url1");
		Image image2 = ImageFixture.createImage("url2");

		List<Image> images = imageRepository.saveAll(List.of(image1, image2));

		VenueImage venueImage1 = VenueFixture.createVenueImage(venue, image1, 1);
		VenueImage venueImage2 = VenueFixture.createVenueImage(venue, image2, 2);

		venue.addVenueImage(venueImage1);
		venue.addVenueImage(venueImage2);

		Venue savedVenue = venueRepository.save(venue);

		// when
		venueFacade.deleteVenue(savedVenue.getId());

	    // then
		Image deletedImage1 = imageRepository.findById(images.get(0).getId()).get();
		Image deletedImage2 = imageRepository.findById(images.get(1).getId()).get();
		assertThat(deletedImage1).extracting("status").isEqualTo(ImageStatus.DELETED);
		assertThat(deletedImage2).extracting("status").isEqualTo(ImageStatus.DELETED);
	}
}
