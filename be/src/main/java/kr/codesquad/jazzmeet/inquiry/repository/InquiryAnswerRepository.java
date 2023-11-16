package kr.codesquad.jazzmeet.inquiry.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import kr.codesquad.jazzmeet.inquiry.entity.Answer;

public interface InquiryAnswerRepository extends JpaRepository<Answer, Long> {

	@Query("select a from Answer a join fetch a.inquiry where a.id = :answerId")
	Optional<Answer> findByIdWithInquiry(@Param("answerId") Long answerId);
}
