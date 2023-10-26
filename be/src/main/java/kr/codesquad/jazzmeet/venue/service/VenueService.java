package kr.codesquad.jazzmeet.venue.service;

import java.util.List;

import org.springframework.stereotype.Service;

import kr.codesquad.jazzmeet.venue.dto.response.VenueAutocompleteResponse;
import kr.codesquad.jazzmeet.venue.entity.Venue;
import kr.codesquad.jazzmeet.venue.mapper.VenueMapper;
import kr.codesquad.jazzmeet.venue.repository.VenueRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class VenueService {

	private final VenueRepository venueRepository;

	public List<VenueAutocompleteResponse> searchAutocompleteList(String word) {

		List<Venue> venues = venueRepository.findTop10ByNameContainingOrRoadNameAddressContaining(word,
			word);

		return venues.stream()
			.map(VenueMapper.INSTANCE::toVenueAutocompleteResponse)
			.toList();
	}
}
