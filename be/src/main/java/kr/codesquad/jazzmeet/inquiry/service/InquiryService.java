package kr.codesquad.jazzmeet.inquiry.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.codesquad.jazzmeet.global.error.CustomException;
import kr.codesquad.jazzmeet.global.error.statuscode.InquiryErrorCode;
import kr.codesquad.jazzmeet.inquiry.dto.request.InquirySaveRequest;
import kr.codesquad.jazzmeet.inquiry.dto.response.InquiryAnswerDetail;
import kr.codesquad.jazzmeet.inquiry.dto.response.InquiryDetailResponse;
import kr.codesquad.jazzmeet.inquiry.dto.response.InquirySaveResponse;
import kr.codesquad.jazzmeet.inquiry.dto.response.InquirySearch;
import kr.codesquad.jazzmeet.inquiry.dto.response.InquirySearchResponse;
import kr.codesquad.jazzmeet.inquiry.entity.Inquiry;
import kr.codesquad.jazzmeet.inquiry.mapper.InquiryMapper;
import kr.codesquad.jazzmeet.inquiry.repository.InquiryQueryRepository;
import kr.codesquad.jazzmeet.inquiry.repository.InquiryRepository;
import kr.codesquad.jazzmeet.inquiry.util.EncryptPasswordEncoder;
import kr.codesquad.jazzmeet.inquiry.util.InquiryCategory;
import kr.codesquad.jazzmeet.inquiry.vo.InquiryDetail;
import kr.codesquad.jazzmeet.inquiry.vo.InquirySearchData;
import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class InquiryService {
	private static final int PAGE_NUMBER_OFFSET = 1;
	private static final int PAGE_SIZE = 10;

	private final InquiryQueryRepository inquiryQueryRepository;
	private final InquiryRepository inquiryRepository;
	private final EncryptPasswordEncoder encryptPasswordEncoder;

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

	public InquiryDetailResponse getInquiryDetail(Long inquiryId) {
		InquiryDetail inquiry = inquiryQueryRepository.findInquiryAndAnswerByInquiryId(inquiryId)
			.orElseThrow(() -> new CustomException(InquiryErrorCode.NOT_FOUND_INQUIRY));
		InquiryAnswerDetail inquiryAnswer = InquiryMapper.INSTANCE.toInquiryAnswerDetail(inquiry);

		return InquiryMapper.INSTANCE.toInquiryDetailResponse(inquiry.getInquiryId(), inquiry.getInquiryContent(),
			inquiryAnswer);
	}

	@Transactional
	public InquirySaveResponse save(InquirySaveRequest inquirySaveRequest) {
		String encryptedPwd = encryptPasswordEncoder.encode(inquirySaveRequest.password());
		InquiryCategory inquiryCategory = InquiryCategory.toInquiryCategory(inquirySaveRequest.category());
		Inquiry inquiry = InquiryMapper.INSTANCE.toInquiry(inquirySaveRequest, inquiryCategory, encryptedPwd);
		Inquiry savedInquiry = inquiryRepository.save(inquiry);

		return InquiryMapper.INSTANCE.toInquirySaveResponse(savedInquiry);
	}

}
