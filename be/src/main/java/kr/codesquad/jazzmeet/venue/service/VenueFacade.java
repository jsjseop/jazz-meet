package kr.codesquad.jazzmeet.venue.service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.codesquad.jazzmeet.global.error.CustomException;
import kr.codesquad.jazzmeet.global.error.statuscode.ImageErrorCode;
import kr.codesquad.jazzmeet.image.entity.Image;
import kr.codesquad.jazzmeet.image.service.ImageService;
import kr.codesquad.jazzmeet.venue.dto.request.VenueCreateHour;
import kr.codesquad.jazzmeet.venue.dto.request.VenueCreateLink;
import kr.codesquad.jazzmeet.venue.dto.request.VenueCreateRequest;
import kr.codesquad.jazzmeet.venue.dto.response.VenueCreateResponse;
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
		validateImagesCount(imageIds);
		Image thumbnailImage = imageService.findById(imageIds.get(THUMBNAIL_IMAGE_INDEX));

		Venue venue = VenueMapper.INSTANCE.toVenue(venueCreateRequest, location, thumbnailImage.getUrl());

		addVenueImages(venue, imageIds);

		List<VenueCreateLink> links = venueCreateRequest.links();
		addVenueLinks(venue, links);

		List<VenueCreateHour> venueHours = venueCreateRequest.venueHours();
		addVenueHours(venue, venueHours);

		Venue savedVenue = venueService.save(venue);
		return new VenueCreateResponse(savedVenue.getId());
	}

	private void validateImagesCount(List<Long> imageIds) {
		if (imageIds.size() > 10) {
			throw new CustomException(ImageErrorCode.IMAGE_LIMIT_EXCEEDED);
		}
	}

	private void addVenueImages(Venue venue, List<Long> imageIds) {
		AtomicLong imageOrder = new AtomicLong();
		imageIds.forEach(imageId -> {
			Image image = imageService.findById(imageId);
			VenueImage venueImage = VenueImage.builder()
				.imageOrder(imageOrder.incrementAndGet())
				.image(image)
				.build();
			venue.addVenueImage(venueImage);
		});
	}

	private void addVenueLinks(Venue venue, List<VenueCreateLink> links) {
		links.forEach(venueCreateLink -> {
			String type = venueCreateLink.type();
			LinkType linkType = linkTypeService.findByName(type);
			Link link = Link.builder()
				.url(venueCreateLink.url())
				.linkType(linkType)
				.build();
			venue.addLink(link);
		});
	}

	private void addVenueHours(Venue venue, List<VenueCreateHour> venueHours) {
		venueHours.forEach(venueCreateHour -> {
			VenueHour venueHour = VenueHour.builder()
				.day(DayOfWeek.toDayOfWeek(venueCreateHour.day()))
				.businessHour(venueCreateHour.businessHours())
				.build();
			venue.addVenueHour(venueHour);
		});
	}
}
