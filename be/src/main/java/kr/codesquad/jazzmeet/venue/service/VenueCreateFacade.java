package kr.codesquad.jazzmeet.venue.service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

@Transactional
@RequiredArgsConstructor
@Service
public class VenueCreateFacade {

	private static final int THUMBNAIL_IMAGE_INDEX = 0;

	private final VenueService venueService;
	private final ImageService imageService;
	private final LinkTypeService linkTypeService;

	public VenueCreateResponse createVenue(VenueCreateRequest venueCreateRequest) {
		Point location = VenueUtil.createPoint(venueCreateRequest.latitude(), venueCreateRequest.longitude());

		List<Long> imageIds = venueCreateRequest.imageIds();
		Image thumbnailImage = imageService.findById(imageIds.get(THUMBNAIL_IMAGE_INDEX));

		Venue venue = VenueMapper.INSTANCE.toVenue(venueCreateRequest, location, thumbnailImage.getUrl());

		AtomicLong imageOrder = new AtomicLong(0L);
		imageIds.forEach(imageId -> {
			Image image = imageService.findById(imageId);
			VenueImage venueImage = VenueImage.builder()
				.imageOrder(imageOrder.incrementAndGet())
				.venue(venue)
				.image(image)
				.build();
			venue.addVenueImage(venueImage);
		});

		List<VenueCreateLink> links = venueCreateRequest.links();
		links.forEach(venueCreateLink -> {
			String type = venueCreateLink.type();
			LinkType linkType = linkTypeService.findByName(type);
			Link link = Link.builder()
				.url(venueCreateLink.url())
				.linkType(linkType)
				.venue(venue)
				.build();
			venue.addLink(link);
		});

		List<VenueCreateHour> venueHours = venueCreateRequest.venueHours();
		venueHours.forEach(venueCreateHour -> {
			VenueHour venueHour = VenueHour.builder()
				.day(DayOfWeek.toDayOfWeek(venueCreateHour.day()))
				.businessHour(venueCreateHour.businessHours())
				.venue(venue)
				.build();
			venue.addVenueHour(venueHour);
		});

		Venue savedVenue = venueService.save(venue);
		return new VenueCreateResponse(savedVenue.getId());
	}
}
