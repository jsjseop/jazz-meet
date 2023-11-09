package kr.codesquad.jazzmeet.inquiry.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.codesquad.jazzmeet.inquiry.entity.Answer;

public interface InquiryAnswerRepository extends JpaRepository<Answer, Long> {
}
