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
		"select venue.id as id, venue.name as name, venue.road_name_address as address, venue.location as location, image.url as thumbnailUrl "
			+ "from venue venue "
			+ "left join venue_image venue_image on (venue.id = venue_image.venue_id and venue_image.image_order = 1) "
			+ "left join image image on (venue_image.image_id = image.id) "
			+ "order by st_distance_sphere(location, point(:longitude, :latitude)) "
			+ "limit 10",
		nativeQuery = true)
	List<NearbyVenue> findNearbyVenuesByLocation(@Param("latitude") Double latitude,
		@Param("longitude") Double longitude);
}
