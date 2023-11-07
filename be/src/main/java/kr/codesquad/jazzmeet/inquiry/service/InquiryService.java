package kr.codesquad.jazzmeet.inquiry.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import kr.codesquad.jazzmeet.inquiry.dto.response.InquirySearch;
import kr.codesquad.jazzmeet.inquiry.dto.response.InquirySearchResponse;
import kr.codesquad.jazzmeet.inquiry.mapper.InquiryMapper;
import kr.codesquad.jazzmeet.inquiry.repository.InquiryQueryRepository;
import kr.codesquad.jazzmeet.inquiry.util.InquiryCategory;
import kr.codesquad.jazzmeet.inquiry.vo.InquirySearchData;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class InquiryService {
	private static final int PAGE_NUMBER_OFFSET = 1;
	private static final int PAGE_SIZE = 10;

	private final InquiryQueryRepository inquiryQueryRepository;

	public InquirySearchResponse getInquiries(String category, String word, int page) {
		// request는 한글, DB 저장은 영어로 되어있기 때문에 변환 필요.
		InquiryCategory inquiryCategory = InquiryCategory.toInquiryCategory(category);
		// word가 없으면 공백 문자를 넣어 category에 해당하는 모든 문의를 반환한다.
		word = isNullInsertBlank(word);
		PageRequest pageRequest = PageRequest.of(page - PAGE_NUMBER_OFFSET, PAGE_SIZE);

		Page<InquirySearchData> inquirySearchData = inquiryQueryRepository.searchInquiries(word, inquiryCategory,
			pageRequest);
		List<InquirySearch> inquirySearches = inquirySearchData.getContent()
			.stream()
			.map(InquiryMapper.INSTANCE::toInquirySearch).toList();

		return InquiryMapper.INSTANCE.toInquirySearchResponse(inquirySearches, inquirySearchData.getTotalElements(),
			inquirySearchData.getNumber() + PAGE_NUMBER_OFFSET, inquirySearchData.getTotalPages());
	}

	private String isNullInsertBlank(String word) {
		if (word == null) {
			return "";
		}
		return word;
	}
}
