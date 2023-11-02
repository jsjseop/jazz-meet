package kr.codesquad.jazzmeet.image.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.codesquad.jazzmeet.image.entity.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
}
