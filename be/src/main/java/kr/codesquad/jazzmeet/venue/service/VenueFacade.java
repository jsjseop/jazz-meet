package kr.codesquad.jazzmeet.venue.service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.codesquad.jazzmeet.image.entity.Image;
import kr.codesquad.jazzmeet.image.service.ImageService;
import kr.codesquad.jazzmeet.venue.dto.request.VenueCreateRequest;
import kr.codesquad.jazzmeet.venue.dto.request.VenueHourRequest;
import kr.codesquad.jazzmeet.venue.dto.request.VenueLinkRequest;
import kr.codesquad.jazzmeet.venue.dto.request.VenueUpdateRequest;
import kr.codesquad.jazzmeet.venue.dto.response.VenueCreateResponse;
import kr.codesquad.jazzmeet.venue.dto.response.VenueDetailResponse;
import kr.codesquad.jazzmeet.venue.entity.DayOfWeek;
import kr.codesquad.jazzmeet.venue.entity.Link;
import kr.codesquad.jazzmeet.venue.entity.LinkType;
import kr.codesquad.jazzmeet.venue.entity.Venue;
import kr.codesquad.jazzmeet.venue.entity.VenueHour;
import kr.codesquad.jazzmeet.venue.entity.VenueImage;
import kr.codesquad.jazzmeet.venue.mapper.VenueMapper;
import kr.codesquad.jazzmeet.venue.util.VenueUtil;
import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class VenueFacade {

	private static final int THUMBNAIL_IMAGE_INDEX = 0;

	private final VenueService venueService;
	private final ImageService imageService;
	private final LinkTypeService linkTypeService;

	@Transactional
	public VenueCreateResponse createVenue(VenueCreateRequest venueCreateRequest) {
		Point location = VenueUtil.createPoint(venueCreateRequest.latitude(), venueCreateRequest.longitude());

		List<Long> imageIds = venueCreateRequest.imageIds();
		Image thumbnailImage = imageService.findById(imageIds.get(THUMBNAIL_IMAGE_INDEX));

		Venue venue = VenueMapper.INSTANCE.toVenue(venueCreateRequest, location, thumbnailImage.getUrl());

		addVenueImages(venue, imageIds);

		List<VenueLinkRequest> links = venueCreateRequest.links();
		addVenueLinks(venue, links);

		List<VenueHourRequest> venueHours = venueCreateRequest.venueHours();
		addVenueHours(venue, venueHours);

		Venue savedVenue = venueService.save(venue);
		return new VenueCreateResponse(savedVenue.getId());
	}

	@Transactional
	public VenueDetailResponse updateVenue(VenueUpdateRequest venueUpdateRequest, Long venueId) {
		Venue venue = venueService.findById(venueId);
		updateVenueFromRequest(venue, venueUpdateRequest);

		List<Long> imageIds = venueUpdateRequest.imageIds();
		addVenueImages(venue, imageIds);

		List<VenueLinkRequest> links = venueUpdateRequest.links();
		addVenueLinks(venue, links);

		List<VenueHourRequest> venueHours = venueUpdateRequest.venueHours();
		addVenueHours(venue, venueHours);

		Venue savedVenue = venueService.save(venue);
		return venueService.findVenue(savedVenue.getId());
	}

	@Transactional
	public void deleteVenue(Long venueId) {
		Venue venue = venueService.findById(venueId);
		List<Long> imageIds = venue.getImages().stream()
			.map(venueImage -> venueImage.getImage().getId())
			.toList();

		// 공연장 삭제
		venueService.deleteById(venueId);
		// 이미지 soft delete
		imageIds.forEach(imageService::deleteImage);
	}

	private void updateVenueFromRequest(Venue venue, VenueUpdateRequest venueUpdateRequest) {
		Point point = VenueUtil.createPoint(venueUpdateRequest.latitude(), venueUpdateRequest.longitude());

		List<Long> imageIds = venueUpdateRequest.imageIds();
		Image thumbnailImage = imageService.findById(imageIds.get(THUMBNAIL_IMAGE_INDEX));

		venue.updateVenue(
			venueUpdateRequest.name(),
			venueUpdateRequest.roadNameAddress(),
			venueUpdateRequest.lotNumberAddress(),
			venueUpdateRequest.phoneNumber(),
			venueUpdateRequest.description(),
			point,
			thumbnailImage.getUrl()
		);
	}

	private void addVenueImages(Venue venue, List<Long> imageIds) {
		AtomicLong imageOrder = new AtomicLong();
		imageIds.forEach(imageId -> {
			Image image = imageService.findById(imageId);
			imageService.registerImage(image);
			VenueImage venueImage = VenueImage.builder()
				.imageOrder(imageOrder.incrementAndGet())
				.image(image)
				.build();
			venue.addVenueImage(venueImage);
		});
	}

	private void addVenueLinks(Venue venue, List<VenueLinkRequest> links) {
		links.forEach(venueLinkRequest -> {
			String type = venueLinkRequest.type();
			LinkType linkType = linkTypeService.findByName(type);
			Link link = Link.builder()
				.url(venueLinkRequest.url())
				.linkType(linkType)
				.build();
			venue.addLink(link);
		});
	}

	private void addVenueHours(Venue venue, List<VenueHourRequest> venueHours) {
		venueHours.forEach(venueHourRequest -> {
			VenueHour venueHour = VenueHour.builder()
				.day(DayOfWeek.toDayOfWeek(venueHourRequest.day()))
				.businessHour(venueHourRequest.businessHours())
				.build();
			venue.addVenueHour(venueHour);
		});
	}
}
