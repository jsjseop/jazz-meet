package kr.codesquad.jazzmeet.venue.service;

import org.springframework.stereotype.Service;

import kr.codesquad.jazzmeet.global.error.CustomException;
import kr.codesquad.jazzmeet.global.error.statuscode.VenueErrorCode;
import kr.codesquad.jazzmeet.venue.entity.LinkType;
import kr.codesquad.jazzmeet.venue.repository.LinkTypeRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LinkTypeService {

	private final LinkTypeRepository linkTypeRepository;

	public LinkType findByName(String name) {
		return linkTypeRepository.findByName(name)
			.orElseThrow(() -> new CustomException(VenueErrorCode.NOT_FOUND_LINK_TYPE));
	}
}
