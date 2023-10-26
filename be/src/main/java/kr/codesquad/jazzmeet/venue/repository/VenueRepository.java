package kr.codesquad.jazzmeet.venue.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import kr.codesquad.jazzmeet.venue.entity.Venue;

public interface VenueRepository extends CrudRepository<Venue, Long> {
	List<Venue> findTop10ByNameContainingOrRoadNameAddressContaining(String nameWord, String roadNameAddressWord);
}
