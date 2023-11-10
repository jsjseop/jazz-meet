package kr.codesquad.jazzmeet.inquiry.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import kr.codesquad.jazzmeet.IntegrationTestSupport;
import kr.codesquad.jazzmeet.fixture.InquiryFixture;
import kr.codesquad.jazzmeet.global.error.CustomException;
import kr.codesquad.jazzmeet.global.error.statuscode.InquiryErrorCode;
import kr.codesquad.jazzmeet.inquiry.dto.request.InquirySaveRequest;
import kr.codesquad.jazzmeet.inquiry.dto.response.InquiryDetailResponse;
import kr.codesquad.jazzmeet.inquiry.dto.response.InquirySaveResponse;
import kr.codesquad.jazzmeet.inquiry.dto.response.InquirySearch;
import kr.codesquad.jazzmeet.inquiry.dto.response.InquirySearchResponse;
import kr.codesquad.jazzmeet.inquiry.entity.Answer;
import kr.codesquad.jazzmeet.inquiry.entity.Inquiry;
import kr.codesquad.jazzmeet.inquiry.repository.InquiryAnswerRepository;
import kr.codesquad.jazzmeet.inquiry.repository.InquiryRepository;
import kr.codesquad.jazzmeet.inquiry.util.EncryptPasswordEncoder;
import kr.codesquad.jazzmeet.inquiry.util.InquiryCategory;
import kr.codesquad.jazzmeet.inquiry.util.InquiryStatus;

class InquiryServiceTest extends IntegrationTestSupport {

	@Autowired
	InquiryService inquiryService;
	@Autowired
	InquiryRepository inquiryRepository;
	@Autowired
	InquiryAnswerRepository inquiryAnswerRepository;
	@Autowired
	EncryptPasswordEncoder encryptPasswordEncoder;

	@AfterEach
	void dbClean() {
		inquiryAnswerRepository.deleteAllInBatch();
		inquiryRepository.deleteAllInBatch();
	}

	@Test
	@DisplayName("카테고리에 해당하는 문의 목록을 조회한다.")
	void getInquiries() {
		// given
		Inquiry inquiry1 = InquiryFixture.createInquiry(InquiryCategory.SERVICE);
		Inquiry inquiry2 = InquiryFixture.createInquiry(InquiryCategory.SERVICE);
		Inquiry inquiry3 = InquiryFixture.createInquiry(InquiryCategory.REGISTRATION);
		Inquiry inquiry4 = InquiryFixture.createInquiry(InquiryCategory.ETC);

		inquiryRepository.save(inquiry1);
		inquiryRepository.save(inquiry2);
		inquiryRepository.save(inquiry3);
		inquiryRepository.save(inquiry4);

		// when
		String category = InquiryCategory.SERVICE.getKoName();
		String word = null;
		int page = 1;

		InquirySearchResponse inquirySearchResponse = inquiryService.getInquiries(category, word, page);

		// then
		assertThat(inquirySearchResponse.inquiries()).hasSize(2);
	}

	@Test
	@DisplayName("내용에 검색어가 포함되는 문의 목록을 조회한다.")
	void searchInquiriesByWordInContent() {
		// given
		Inquiry inquiry1 = InquiryFixture.createInquiry("이미지 수정해주세요", InquiryCategory.SERVICE);
		Inquiry inquiry2 = InquiryFixture.createInquiry("버튼 변경해주세요", InquiryCategory.SERVICE);
		Inquiry inquiry3 = InquiryFixture.createInquiry("전화번호 수정해주세요", InquiryCategory.SERVICE);

		inquiryRepository.save(inquiry1);
		inquiryRepository.save(inquiry2);
		inquiryRepository.save(inquiry3);

		// when
		String category = InquiryCategory.SERVICE.getKoName();
		String word = "수정";
		int page = 1;

		InquirySearchResponse inquirySearchResponse = inquiryService.getInquiries(category, word, page);
		// then
		assertThat(inquirySearchResponse.inquiries())
			.hasSize(2)
			.extracting(InquirySearch::content)
			.doesNotContain(inquiry2.getContent())
			.contains(inquiry1.getContent())
			.contains(inquiry3.getContent());
	}

	@Test
	@DisplayName("닉네임에 검색어가 포함되는 문의 목록을 조회한다.")
	void searchInquiriesByWordInNickname() {
		// given
		Inquiry inquiry1 = InquiryFixture.createInquiry(InquiryCategory.SERVICE, "지안");
		Inquiry inquiry2 = InquiryFixture.createInquiry(InquiryCategory.SERVICE, "이안");
		Inquiry inquiry3 = InquiryFixture.createInquiry(InquiryCategory.SERVICE, "시오");
		Inquiry inquiry4 = InquiryFixture.createInquiry(InquiryCategory.SERVICE, "지안");

		inquiryRepository.save(inquiry1);
		inquiryRepository.save(inquiry2);
		inquiryRepository.save(inquiry3);
		inquiryRepository.save(inquiry4);

		// when
		String category = InquiryCategory.SERVICE.getKoName();
		String word = "지안";
		int page = 1;

		InquirySearchResponse inquirySearchResponse = inquiryService.getInquiries(category, word, page);
		// then
		assertThat(inquirySearchResponse.inquiries())
			.hasSize(2)
			.extracting(InquirySearch::nickname)
			.doesNotContain(inquiry2.getNickname())
			.doesNotContain(inquiry3.getNickname())
			.contains(inquiry1.getNickname())
			.contains(inquiry4.getNickname());
	}

	@Test
	@DisplayName("문의의 상세 정보를 조회한다.")
	void getInquiryDetail() {
		// given
		Inquiry inquiry = InquiryFixture.createInquiry();
		Long inquiryId = inquiryRepository.save(inquiry).getId();
		Answer inquiryAnswer = InquiryFixture.createInquiryAnswer(inquiry);
		Long answerId = inquiryAnswerRepository.save(inquiryAnswer).getId();

		// when
		InquiryDetailResponse inquiryDetail = inquiryService.getInquiryDetail(inquiryId);

		// then
		assertThat(inquiryDetail).isNotNull().extracting("id").isEqualTo(inquiryId);
		assertThat(inquiryDetail.answer()).extracting("id").isEqualTo(answerId);
	}

	@Test
	@DisplayName("존재하지 않는 문의의 상세 정보는 조회되지 않는다.")
	void getInquiryDetailError() {
		// given
		Inquiry inquiry = InquiryFixture.createInquiry();
		inquiryRepository.save(inquiry);
		Answer inquiryAnswer = InquiryFixture.createInquiryAnswer(inquiry);
		inquiryAnswerRepository.save(inquiryAnswer);

		//when //then
		Long wrongInquiryId = 0L;
		assertThatThrownBy(() -> inquiryService.getInquiryDetail(wrongInquiryId))
			.isInstanceOf(CustomException.class).hasMessage(InquiryErrorCode.NOT_FOUND_INQUIRY.getMessage());
	}

	@Test
	@DisplayName("문의를 등록하면 해당 문의 정보를 반환한다.")
	void saveInquiry() {
		// given
		String category = "서비스";
		String nickname = "지안";
		String password = "1234";
		String content = "문의 내용";
		InquirySaveRequest inquirySaveRequest = InquiryFixture.createInquiryRequest(category, nickname, password,
			content);

		// when
		InquirySaveResponse saved = inquiryService.save(inquirySaveRequest);

		// then
		assertThat(saved)
			.extracting("nickname", "content", "status")
			.containsExactly(nickname, content, InquiryStatus.WAITING.getKoName());
	}

	@Test
	@DisplayName("문의를 등록하면 비밀번호는 암호화되어 저장된다.")
	void saveInquiryValidRequest() {
		// given
		String category = "서비스";
		String nickname = "지안";
		String password = "비밀번호";
		String content = "문의 내용";
		InquirySaveRequest inquirySaveRequest = InquiryFixture.createInquiryRequest(category, nickname, password,
			content);

		String encryptedPwd = encryptPasswordEncoder.encode(password);

		// when
		InquirySaveResponse saved = inquiryService.save(inquirySaveRequest);
		String savedEncryptedPwd = inquiryRepository.findById(saved.id()).get().getPassword();

		// then
		assertThat(savedEncryptedPwd).isEqualTo(encryptedPwd);
	}

	@Test
	@DisplayName("해당하는 카테고리가 없는 문의를 등록하면 문의가 등록되지 않는다.")
	void saveInquiryValidCategory() {
		// given
		String category = "없는 카테고리";
		String nickname = "지안";
		String password = "비밀번호";
		String content = "문의 내용";
		InquirySaveRequest inquirySaveRequest = InquiryFixture.createInquiryRequest(category, nickname, password,
			content);

		// when // then
		assertThatThrownBy(() -> inquiryService.save(inquirySaveRequest))
			.isInstanceOf(CustomException.class)
			.hasMessage(InquiryErrorCode.NO_MATCH_VALUE.getMessage());
	}
}
