package kr.codesquad.jazzmeet.venue.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.codesquad.jazzmeet.venue.entity.Link;
import kr.codesquad.jazzmeet.venue.entity.LinkType;

public interface LinkRepository extends JpaRepository<Link, Long> {
	List<Link> findByVenueIsNotNullAndLinkType(LinkType linkType);
}
