package kr.codesquad.jazzmeet.venue.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.codesquad.jazzmeet.venue.entity.Venue;

public interface VenueRepository extends JpaRepository<Venue, Long> {
	List<Venue> findTop10ByNameContainingOrRoadNameAddressContaining(String nameWord, String roadNameAddressWord);
}
