package kr.codesquad.jazzmeet.inquiry.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import kr.codesquad.jazzmeet.inquiry.dto.response.InquiryAnswerDetail;
import kr.codesquad.jazzmeet.inquiry.dto.response.InquiryDetailResponse;
import kr.codesquad.jazzmeet.inquiry.dto.response.InquirySearch;
import kr.codesquad.jazzmeet.inquiry.dto.response.InquirySearchResponse;
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
}
