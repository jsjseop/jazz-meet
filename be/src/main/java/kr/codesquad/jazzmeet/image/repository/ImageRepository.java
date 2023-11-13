package kr.codesquad.jazzmeet.image.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.codesquad.jazzmeet.image.entity.Image;
import kr.codesquad.jazzmeet.image.entity.ImageStatus;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

	Optional<Image> findByIdAndStatusNot(Long imageId, ImageStatus status);
}
