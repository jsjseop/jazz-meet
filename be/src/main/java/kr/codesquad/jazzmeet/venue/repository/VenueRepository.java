package kr.codesquad.jazzmeet.venue.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import kr.codesquad.jazzmeet.venue.entity.Venue;
import kr.codesquad.jazzmeet.venue.vo.NearbyVenue;

@Repository
public interface VenueRepository extends JpaRepository<Venue, Long> {
	List<Venue> findTop10ByNameContainingOrRoadNameAddressContaining(String nameWord, String roadNameAddressWord);

	@Query(value =
		"select id, name, road_name_address as address, location, thumbnail_url "
			+ "from venue "
			+ "order by st_distance_sphere(location, point(:longitude, :latitude)) "
			+ "limit 10",
		nativeQuery = true)
	List<NearbyVenue> findNearbyVenuesByLocation(@Param("latitude") Double latitude,
		@Param("longitude") Double longitude);
}
