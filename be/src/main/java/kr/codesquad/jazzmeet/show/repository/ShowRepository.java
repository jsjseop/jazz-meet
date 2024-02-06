package kr.codesquad.jazzmeet.show.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import kr.codesquad.jazzmeet.show.entity.Show;

@Repository
public interface ShowRepository extends JpaRepository<Show, Long> {

	@Query("select s from Show s join fetch s.poster where s.venue.id = :venueId and FUNCTION('DATE', s.startTime) = :date")
	List<Show> findByVenueIdAndDate(@Param("venueId") Long venueId, @Param("date") LocalDate date);

	@Query("select s from Show s join fetch s.venue join fetch s.poster where s.id = :showId")
	Optional<Show> findEntireShowById(@Param("showId") Long showId);

	@Query("select s from Show s where s.startTime = (select MAX(s2.startTime) from Show s2) and s.venue.id = :venueId")
	Optional<Show> findLatestShowByVenueId(Long venueId);
}
