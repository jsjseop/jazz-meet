package kr.codesquad.jazzmeet.inquiry.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import kr.codesquad.jazzmeet.inquiry.dto.response.InquirySearchResponse;
import kr.codesquad.jazzmeet.inquiry.mapper.InquiryMapper;
import kr.codesquad.jazzmeet.inquiry.repository.InquiryQueryRepository;
import kr.codesquad.jazzmeet.inquiry.vo.InquirySearch;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class InquiryService {
	private static final int PAGE_NUMBER_OFFSET = 1;
	private static final int PAGE_SIZE = 10;

	private final InquiryQueryRepository inquiryQueryRepository;

	public InquirySearchResponse getInquiries(String category, String word, int page) {
		// TODO: category enum에서 확인하고 오류 반환
		// TODO: InquiryStatus, InquiryCategory 적용
		PageRequest pageRequest = PageRequest.of(page - PAGE_NUMBER_OFFSET, PAGE_SIZE);
		Page<InquirySearch> inquirySearches = inquiryQueryRepository.searchInquiries(word, category, pageRequest);

		return InquiryMapper.INSTANCE.toInquirySearchResponse(inquirySearches.getContent(),
			inquirySearches.getTotalElements(), inquirySearches.getNumber() + PAGE_NUMBER_OFFSET,
			inquirySearches.getTotalPages());

	}
}
