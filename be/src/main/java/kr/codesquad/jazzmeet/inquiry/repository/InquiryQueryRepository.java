package kr.codesquad.jazzmeet.inquiry.repository;

import static kr.codesquad.jazzmeet.inquiry.entity.QAnswer.*;
import static kr.codesquad.jazzmeet.inquiry.entity.QInquiry.*;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import kr.codesquad.jazzmeet.inquiry.util.InquiryCategory;
import kr.codesquad.jazzmeet.inquiry.util.InquiryStatus;
import kr.codesquad.jazzmeet.inquiry.vo.InquiryDetail;
import kr.codesquad.jazzmeet.inquiry.vo.InquirySearchData;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class InquiryQueryRepository {

	private final JPAQueryFactory query;

	public Page<InquirySearchData> searchInquiries(InquiryCategory category, String word, InquiryStatus status,
		Pageable pageable) {
		List<InquirySearchData> inquiries = query.select(
				Projections.fields(InquirySearchData.class,
					inquiry.id,
					inquiry.status,
					inquiry.content,
					inquiry.nickname,
					inquiry.createdAt
				))
			.from(inquiry)
			.where(isContainNickNameOrContent(word)
				.and(isEqualsCategory(category))
				.and(isEqualsStatus(status))
				.and(isNotDeleted()))
			.limit(pageable.getPageSize())
			.offset(pageable.getOffset())
			.orderBy(inquiry.createdAt.desc()).fetch();

		JPAQuery<Long> inquiriesByWordCount = countInquiries(word);

		return PageableExecutionUtils.getPage(inquiries, pageable, inquiriesByWordCount::fetchOne);
	}

	private BooleanExpression isNotDeleted() {
		return inquiry.status.ne(InquiryStatus.DELETED);
	}

	private JPAQuery<Long> countInquiries(String word) {
		return query.select(inquiry.count())
			.from(inquiry)
			.where(isContainNickNameOrContent(word));
	}

	private BooleanBuilder isContainNickNameOrContent(String word) {
		BooleanBuilder booleanBuilder = new BooleanBuilder();
		booleanBuilder.or(isContainWordInContent(word));
		booleanBuilder.or(isContainWordInNickname(word));

		return booleanBuilder;
	}

	private BooleanExpression isContainWordInNickname(String word) {
		if (word == null || word.equals("")) {
			return null;
		}
		return inquiry.nickname.contains(word);
	}

	private BooleanExpression isContainWordInContent(String word) {
		if (word == null || word.equals("")) {
			return null;
		}
		return inquiry.content.contains(word);
	}

	private BooleanExpression isEqualsCategory(InquiryCategory category) {
		if (category == null || category.equals("")) {
			return null;
		}
		return inquiry.category.eq(category);
	}

	private BooleanExpression isEqualsStatus(InquiryStatus status) {
		if (status == null || status.equals("")) {
			return null;
		}
		return inquiry.status.eq(status);
	}

	public Optional<InquiryDetail> findInquiryAndAnswerByInquiryId(Long inquiryId) {
		InquiryDetail inquiryDetail = query.select(Projections.fields(InquiryDetail.class,
				inquiry.id,
				inquiry.status,
				inquiry.content,
				inquiry.nickname,
				inquiry.createdAt,
				answer.id.as("answerId"),
				answer.content.as("answerContent"),
				answer.createdAt.as("answerCreatedAt"),
				answer.modifiedAt.as("answerModifiedAt")
			))
			.from(inquiry)
			.leftJoin(answer).on(answer.inquiry.id.eq(inquiryId))
			.where(inquiry.id.eq(inquiryId).and(isNotDeleted())).fetchOne();

		return Optional.ofNullable(inquiryDetail);
	}

}
