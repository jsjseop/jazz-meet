package kr.codesquad.jazzmeet.venue.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.codesquad.jazzmeet.venue.entity.LinkType;

@Repository
public interface LinkTypeRepository extends JpaRepository<LinkType, Long> {

	Optional<LinkType> findByName(String name);
}
