package kr.codesquad.jazzmeet.inquiry.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.codesquad.jazzmeet.inquiry.entity.Inquiry;

public interface InquiryRepository extends JpaRepository<Inquiry, Long> {
}
