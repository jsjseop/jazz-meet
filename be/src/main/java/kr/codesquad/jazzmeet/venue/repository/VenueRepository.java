package kr.codesquad.jazzmeet.venue.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.codesquad.jazzmeet.venue.entity.Venue;

@Repository
public interface VenueRepository extends JpaRepository<Venue, Long> {
	
	List<Venue> findTop10ByNameContainingOrRoadNameAddressContaining(String nameWord, String roadNameAddressWord);
}
