package kr.codesquad.jazzmeet.inquiry.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import kr.codesquad.jazzmeet.inquiry.dto.request.InquirySaveRequest;
import kr.codesquad.jazzmeet.inquiry.dto.response.InquiryDetailResponse;
import kr.codesquad.jazzmeet.inquiry.dto.response.InquirySaveResponse;
import kr.codesquad.jazzmeet.inquiry.dto.response.InquirySearch;
import kr.codesquad.jazzmeet.inquiry.dto.response.InquirySearchResponse;
import kr.codesquad.jazzmeet.inquiry.dto.response.answer.InquiryAnswerDetail;
import kr.codesquad.jazzmeet.inquiry.dto.response.answer.InquiryAnswerSaveResponse;
import kr.codesquad.jazzmeet.inquiry.dto.response.answer.InquiryAnswerUpdateResponse;
import kr.codesquad.jazzmeet.inquiry.entity.Answer;
import kr.codesquad.jazzmeet.inquiry.entity.Inquiry;
import kr.codesquad.jazzmeet.inquiry.util.InquiryCategory;
import kr.codesquad.jazzmeet.inquiry.vo.InquiryDetail;
import kr.codesquad.jazzmeet.inquiry.vo.InquirySearchData;

@Mapper
public interface InquiryMapper {
	InquiryMapper INSTANCE = Mappers.getMapper(InquiryMapper.class);

	default InquirySearchResponse toInquirySearchResponse(List<InquirySearch> inquiries,
		long totalCount, int currentPage, int maxPage) {
		Integer dummy = null;
		return toInquirySearchResponse(dummy, inquiries, totalCount, currentPage, maxPage);
	}

	@Mapping(target = "inquiries", source = "inquiries")
	@Mapping(target = "totalCount", source = "totalCount")
	@Mapping(target = "currentPage", source = "currentPage")
	@Mapping(target = "maxPage", source = "maxPage")
	InquirySearchResponse toInquirySearchResponse(Integer dummy, List<InquirySearch> inquiries,
		long totalCount, int currentPage, int maxPage);

	@Mapping(target = "status", source = "status.koName")
	InquirySearch toInquirySearch(InquirySearchData inquirySearchData);

	InquiryAnswerDetail toInquiryAnswerDetail(InquiryDetail inquiry);

	@Mapping(target = "id", source = "inquiryId")
	@Mapping(target = "content", source = "inquiryContent")
	@Mapping(target = "answer", source = "answer")
	InquiryDetailResponse toInquiryDetailResponse(Long inquiryId, String inquiryContent,
		InquiryAnswerDetail answer);

	@Mapping(target = "category", source = "inquiryCategory")
	@Mapping(target = "password", source = "encryptedPwd")
	@Mapping(ignore = true, target = "status")
	Inquiry toInquiry(InquirySaveRequest inquirySaveRequest, InquiryCategory inquiryCategory, String encryptedPwd);

	@Mapping(target = "status", source = "status.koName")
	InquirySaveResponse toInquirySaveResponse(Inquiry savedInquiry);

	@Mapping(target = "content", source = "content")
	@Mapping(target = "inquiry", source = "inquiry")
	Answer toAnswer(String content, Inquiry inquiry, Long adminId);

	InquiryAnswerSaveResponse toInquiryAnswerSaveResponse(Answer answer);

	InquiryAnswerUpdateResponse toInquiryAnswerUpdateResponse(Answer answer);
}
