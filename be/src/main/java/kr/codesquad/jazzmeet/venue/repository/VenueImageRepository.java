package kr.codesquad.jazzmeet.venue.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.codesquad.jazzmeet.venue.entity.VenueImage;

@Repository
public interface VenueImageRepository extends JpaRepository<VenueImage, Long> {
}
