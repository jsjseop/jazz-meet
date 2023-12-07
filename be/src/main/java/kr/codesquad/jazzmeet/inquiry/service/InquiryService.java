package kr.codesquad.jazzmeet.inquiry.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.codesquad.jazzmeet.global.error.CustomException;
import kr.codesquad.jazzmeet.global.error.statuscode.InquiryErrorCode;
import kr.codesquad.jazzmeet.global.util.PasswordEncoder;
import kr.codesquad.jazzmeet.inquiry.dto.request.InquiryDeleteRequest;
import kr.codesquad.jazzmeet.inquiry.dto.request.InquirySaveRequest;
import kr.codesquad.jazzmeet.inquiry.dto.request.answer.InquiryAnswerSaveRequest;
import kr.codesquad.jazzmeet.inquiry.dto.request.answer.InquiryAnswerUpdateRequest;
import kr.codesquad.jazzmeet.inquiry.dto.response.InquiryDetailResponse;
import kr.codesquad.jazzmeet.inquiry.dto.response.InquirySaveResponse;
import kr.codesquad.jazzmeet.inquiry.dto.response.InquirySearch;
import kr.codesquad.jazzmeet.inquiry.dto.response.InquirySearchResponse;
import kr.codesquad.jazzmeet.inquiry.dto.response.answer.InquiryAnswerDetail;
import kr.codesquad.jazzmeet.inquiry.dto.response.answer.InquiryAnswerSaveResponse;
import kr.codesquad.jazzmeet.inquiry.dto.response.answer.InquiryAnswerUpdateResponse;
import kr.codesquad.jazzmeet.inquiry.entity.Answer;
import kr.codesquad.jazzmeet.inquiry.entity.Inquiry;
import kr.codesquad.jazzmeet.inquiry.mapper.InquiryMapper;
import kr.codesquad.jazzmeet.inquiry.repository.InquiryAnswerRepository;
import kr.codesquad.jazzmeet.inquiry.repository.InquiryQueryRepository;
import kr.codesquad.jazzmeet.inquiry.repository.InquiryRepository;
import kr.codesquad.jazzmeet.inquiry.util.InquiryCategory;
import kr.codesquad.jazzmeet.inquiry.util.InquiryStatus;
import kr.codesquad.jazzmeet.inquiry.vo.InquiryDetail;
import kr.codesquad.jazzmeet.inquiry.vo.InquirySearchData;
import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class InquiryService {
	private static final int PAGE_NUMBER_OFFSET = 1;
	private static final int PAGE_SIZE = 10;
	private static final Long DEFAULT_ADMIN_ID = 1L;

	private final InquiryQueryRepository inquiryQueryRepository;
	private final InquiryAnswerRepository answerRepository;
	private final InquiryRepository inquiryRepository;

	public InquirySearchResponse getInquiries(String category, String word, int page) {
		// request는 한글, DB 저장은 영어로 되어있기 때문에 변환 필요.
		InquiryCategory inquiryCategory = InquiryCategory.toInquiryCategory(category);
		PageRequest pageRequest = PageRequest.of(page - PAGE_NUMBER_OFFSET, PAGE_SIZE);

		Page<InquirySearchData> inquirySearchData = inquiryQueryRepository.searchInquiries(word, inquiryCategory,
			pageRequest);
		List<InquirySearch> inquirySearches = inquirySearchData.getContent()
			.stream()
			.map(InquiryMapper.INSTANCE::toInquirySearch).toList();

		return InquiryMapper.INSTANCE.toInquirySearchResponse(inquirySearches, inquirySearchData.getTotalElements(),
			inquirySearchData.getNumber() + PAGE_NUMBER_OFFSET, inquirySearchData.getTotalPages());
	}

	public InquiryDetailResponse getInquiryDetail(Long inquiryId) {
		InquiryDetail inquiry = inquiryQueryRepository.findInquiryAndAnswerByInquiryId(inquiryId)
			.orElseThrow(() -> new CustomException(InquiryErrorCode.NOT_FOUND_INQUIRY));
		InquiryAnswerDetail inquiryAnswer = InquiryMapper.INSTANCE.toInquiryAnswerDetail(inquiry)
			.checkAndSetToNull();

		return InquiryMapper.INSTANCE.toInquiryDetailResponse(inquiry, inquiryAnswer);
	}

	@Transactional
	public InquirySaveResponse save(InquirySaveRequest inquirySaveRequest) {
		String encryptedPwd = PasswordEncoder.encode(inquirySaveRequest.password());
		InquiryCategory inquiryCategory = InquiryCategory.toInquiryCategory(inquirySaveRequest.category());
		Inquiry inquiry = InquiryMapper.INSTANCE.toInquiry(inquirySaveRequest, inquiryCategory, encryptedPwd);
		Inquiry savedInquiry = inquiryRepository.save(inquiry);

		return InquiryMapper.INSTANCE.toInquirySaveResponse(savedInquiry);
	}

	@Transactional
	public void delete(Long inquiryId, InquiryDeleteRequest request) {
		Inquiry inquiry = findById(inquiryId);
		inspectDeletedInquiry(inquiry.getStatus());
		PasswordEncoder.matchesPassword(request.password(), inquiry.getPassword());

		inquiry.updateStatusToDeleted();
	}

	@Transactional
	public InquiryAnswerSaveResponse saveAnswer(InquiryAnswerSaveRequest request) {
		Long inquiryId = request.inquiryId();
		Inquiry inquiry = findById(inquiryId).inspectExistAnswer();
		Answer answer = InquiryMapper.INSTANCE.toAnswer(request.content(), inquiry, DEFAULT_ADMIN_ID);
		Answer savedAnswer = answerRepository.save(answer);
		inquiry.updateStatusToReplied(savedAnswer);

		return InquiryMapper.INSTANCE.toInquiryAnswerSaveResponse(savedAnswer);
	}

	@Transactional
	public InquiryAnswerUpdateResponse updateAnswer(Long answerId, InquiryAnswerUpdateRequest request) {
		Answer answer = findAnswerById(answerId);
		answer.updateContent(request.content());
		answerRepository.flush(); // modifiedAt 반영을 위해 변경 사항 강제 커밋

		return InquiryMapper.INSTANCE.toInquiryAnswerUpdateResponse(answer);
	}

	@Transactional
	public void deleteAnswer(Long answerId) {
		Answer answer = findAnswerByIdWithInquiry(answerId);
		Inquiry inquiry = answer.getInquiry();
		inquiry.updateStatusToWaiting();

		answerRepository.delete(answer);
	}

	private Inquiry findById(Long inquiryId) {
		return inquiryRepository.findById(inquiryId)
			.orElseThrow(() -> new CustomException(InquiryErrorCode.NOT_FOUND_INQUIRY));
	}

	private void inspectDeletedInquiry(InquiryStatus status) {
		if (status == InquiryStatus.DELETED) {
			throw new CustomException(InquiryErrorCode.ALREADY_DELETED);
		}
	}

	private Answer findAnswerById(Long answerId) {
		return answerRepository.findById(answerId)
			.orElseThrow(() -> new CustomException(InquiryErrorCode.NOT_FOUND_ANSWER));
	}

	private Answer findAnswerByIdWithInquiry(Long answerId) {
		return answerRepository.findByIdWithInquiry(answerId)
			.orElseThrow(() -> new CustomException(InquiryErrorCode.NOT_FOUND_ANSWER));
	}
}
