package kr.codesquad.jazzmeet.inquiry.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import kr.codesquad.jazzmeet.IntegrationTestSupport;
import kr.codesquad.jazzmeet.admin.entity.Admin;
import kr.codesquad.jazzmeet.fixture.InquiryFixture;
import kr.codesquad.jazzmeet.global.error.CustomException;
import kr.codesquad.jazzmeet.global.error.statuscode.ErrorCode;
import kr.codesquad.jazzmeet.global.error.statuscode.InquiryErrorCode;
import kr.codesquad.jazzmeet.inquiry.dto.request.InquiryDeleteRequest;
import kr.codesquad.jazzmeet.inquiry.dto.request.InquirySaveRequest;
import kr.codesquad.jazzmeet.inquiry.dto.request.answer.InquiryAnswerSaveRequest;
import kr.codesquad.jazzmeet.inquiry.dto.request.answer.InquiryAnswerUpdateRequest;
import kr.codesquad.jazzmeet.inquiry.dto.response.InquiryDetailResponse;
import kr.codesquad.jazzmeet.inquiry.dto.response.InquirySaveResponse;
import kr.codesquad.jazzmeet.inquiry.dto.response.InquirySearch;
import kr.codesquad.jazzmeet.inquiry.dto.response.InquirySearchResponse;
import kr.codesquad.jazzmeet.inquiry.dto.response.answer.InquiryAnswerSaveResponse;
import kr.codesquad.jazzmeet.inquiry.dto.response.answer.InquiryAnswerUpdateResponse;
import kr.codesquad.jazzmeet.inquiry.entity.Answer;
import kr.codesquad.jazzmeet.inquiry.entity.Inquiry;
import kr.codesquad.jazzmeet.inquiry.repository.InquiryAnswerRepository;
import kr.codesquad.jazzmeet.inquiry.repository.InquiryRepository;
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
	BCryptPasswordEncoder bCryptPasswordEncoder;

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
		String status = InquiryStatus.WAITING.getKoName();
		String word = null;
		int page = 1;

		InquirySearchResponse inquirySearchResponse = inquiryService.getInquiries(category, word, status, page);

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
		String status = InquiryStatus.WAITING.getKoName();
		String word = "수정";
		int page = 1;

		InquirySearchResponse inquirySearchResponse = inquiryService.getInquiries(category, word, status, page);
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
		String status = InquiryStatus.WAITING.getKoName();
		String word = "지안";
		int page = 1;

		InquirySearchResponse inquirySearchResponse = inquiryService.getInquiries(category, word, status, page);
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
	@DisplayName("문의 상태에 해당되는 문의 목록을 조회한다.")
	void searchInquiriesByStatus() {
		// given
		Inquiry inquiry1 = InquiryFixture.createInquiry(InquiryCategory.SERVICE);
		Inquiry inquiry2 = InquiryFixture.createInquiry(InquiryCategory.SERVICE);
		Admin admin = InquiryFixture.createAdmin();

		inquiryRepository.save(inquiry1);
		inquiryRepository.save(inquiry2);
		InquiryAnswerSaveRequest request = InquiryFixture.createInquiryAnswerSaveRequest(inquiry1.getId());
		inquiryService.saveAnswer(request, admin);

		// when
		String category = InquiryCategory.SERVICE.getKoName();
		String status = InquiryStatus.WAITING.getKoName();
		String word = null;
		int page = 1;

		InquirySearchResponse inquirySearchResponse = inquiryService.getInquiries(category, word, status, page);
		// then
		assertThat(inquirySearchResponse.inquiries())
			.hasSize(1)
			.extracting(InquirySearch::id)
			.doesNotContain(inquiry1.getId())
			.contains(inquiry2.getId());
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
		InquirySaveRequest inquirySaveRequest = InquiryFixture.createInquirySaveRequest(category, nickname, password,
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
		InquirySaveRequest inquirySaveRequest = InquiryFixture.createInquirySaveRequest(category, nickname, password,
			content);

		// when
		InquirySaveResponse saved = inquiryService.save(inquirySaveRequest);
		String encodedPassword = inquiryRepository.findById(saved.id()).get().getPassword();

		// then
		assertThat(bCryptPasswordEncoder.matches(password, encodedPassword)).isTrue();
	}

	@Test
	@DisplayName("해당하는 카테고리가 없는 문의를 등록하면 문의가 등록되지 않는다.")
	void saveInquiryValidCategory() {
		// given
		String category = "없는 카테고리";
		String nickname = "지안";
		String password = "비밀번호";
		String content = "문의 내용";
		InquirySaveRequest inquirySaveRequest = InquiryFixture.createInquirySaveRequest(category, nickname, password,
			content);

		// when // then
		assertThatThrownBy(() -> inquiryService.save(inquirySaveRequest))
			.isInstanceOf(CustomException.class)
			.hasMessage(InquiryErrorCode.NO_MATCH_VALUE.getMessage());
	}

	@Test
	@DisplayName("비밀번호가 일치하면 문의가 삭제된다.")
	void delete() {
		// given
		String password = "비밀번호 테스트";
		Inquiry inquiry = InquiryFixture.createInquiry(bCryptPasswordEncoder.encode(password));
		Long inquiryId = inquiryRepository.save(inquiry).getId();
		InquiryDeleteRequest inquiryDeleteRequest = InquiryFixture.createInquiryDeleteRequest(password);

		// when
		inquiryService.delete(inquiryId, inquiryDeleteRequest, null);
		Inquiry savedInquiry = inquiryRepository.findById(inquiryId).get();

		// then
		assertAll(
			() -> assertThat(savedInquiry.getStatus())
				.isEqualTo(InquiryStatus.DELETED),
			() -> assertThat(bCryptPasswordEncoder.matches(password, savedInquiry.getPassword()))
				.isTrue()
		);
	}

	@Test
	@DisplayName("비밀번호가 일치하면 문의가 삭제된다.")
	void deleteInquiryAdminPermission() {
		// given
		String password = "비밀번호 테스트";
		Inquiry inquiry = InquiryFixture.createInquiry(bCryptPasswordEncoder.encode(password));
		Long inquiryId = inquiryRepository.save(inquiry).getId();
		InquiryDeleteRequest inquiryDeleteRequest = InquiryFixture.createInquiryDeleteRequest(password);

		// when
		inquiryService.delete(inquiryId, inquiryDeleteRequest, null);
		Inquiry savedInquiry = inquiryRepository.findById(inquiryId).get();

		// then
		assertAll(
			() -> assertThat(savedInquiry.getStatus())
				.isEqualTo(InquiryStatus.DELETED),
			() -> assertThat(bCryptPasswordEncoder.matches(password, savedInquiry.getPassword()))
				.isTrue()
		);
	}

	@Test
	@DisplayName("존재하지 않는 문의는 삭제할 수 없다.")
	void deleteNotFoundException() {
		// given
		String password = "비밀번호 테스트";
		Long inquiryId = 0L;
		InquiryDeleteRequest inquiryDeleteRequest = InquiryFixture.createInquiryDeleteRequest(password);

		// when // then
		assertThatThrownBy(() -> inquiryService.delete(inquiryId, inquiryDeleteRequest, null))
			.isInstanceOf(CustomException.class)
			.hasMessage(InquiryErrorCode.NOT_FOUND_INQUIRY.getMessage());

	}

	@Test
	@DisplayName("이미 삭제 된 문의는 삭제할 수 없다.")
	void deleteAlreadyDeletedException() {
		// given
		String password = "비밀번호 테스트";
		InquiryStatus status = InquiryStatus.DELETED;
		Inquiry inquiry = InquiryFixture.createInquiry(bCryptPasswordEncoder.encode(password), status);
		Long inquiryId = inquiryRepository.save(inquiry).getId();
		InquiryDeleteRequest inquiryDeleteRequest = InquiryFixture.createInquiryDeleteRequest(password);

		// when // then
		assertThatThrownBy(() -> inquiryService.delete(inquiryId, inquiryDeleteRequest, null))
			.isInstanceOf(CustomException.class)
			.hasMessage(InquiryErrorCode.ALREADY_DELETED.getMessage());

	}

	@Test
	@DisplayName("비밀번호가 일치하지 않는 문의는 삭제할 수 없다.")
	void deleteNotMatchedPasswordException() {
		// given
		String password = "비밀번호 테스트";
		Inquiry inquiry = InquiryFixture.createInquiry(bCryptPasswordEncoder.encode(password));
		Long inquiryId = inquiryRepository.save(inquiry).getId();
		String wrongPassword = "잘못 된 비밀번호";
		InquiryDeleteRequest inquiryDeleteRequest = InquiryFixture.createInquiryDeleteRequest(wrongPassword);

		// when // then
		assertThatThrownBy(() -> inquiryService.delete(inquiryId, inquiryDeleteRequest, null))
			.isInstanceOf(CustomException.class)
			.hasMessage(ErrorCode.WRONG_PASSWORD.getMessage());
	}

	@Test
	@DisplayName("문의에 답변을 등록할 수 있다.")
	void saveAnswer() {
		// given
		Long inquiryId = inquiryRepository.save(InquiryFixture.createInquiry()).getId();
		String content = "문의 답변 내용";
		InquiryAnswerSaveRequest request = InquiryFixture.createInquiryAnswerSaveRequest(inquiryId, content);
		Admin admin = InquiryFixture.createAdmin();

		// when
		InquiryAnswerSaveResponse response = inquiryService.saveAnswer(request, admin);

		Inquiry inquiry = inquiryRepository.findById(inquiryId).get();
		Answer answer = inquiryAnswerRepository.findById(response.id()).get();

		// then
		assertAll(
			() -> assertThat(inquiry.getAnswer())
				.usingRecursiveComparison()
				.ignoringFields("inquiry")
				.isEqualTo(answer),
			() -> assertThat(inquiry.getStatus()).isEqualTo(InquiryStatus.REPLIED),
			() -> assertThat(response.content()).isEqualTo(content),
			() -> assertThat(response.createdAt()).isNotNull()
		);
	}

	@Test
	@DisplayName("존재하지 않는 문의에 대한 답변은 등록할 수 없다.")
	void saveAnswerNotExistInquiryException() {
		// given
		Long inquiryId = 0L;
		String content = "문의 답변 내용";
		InquiryAnswerSaveRequest request = InquiryFixture.createInquiryAnswerSaveRequest(inquiryId, content);
		Admin admin = InquiryFixture.createAdmin();

		// when // then
		assertThatThrownBy(() -> inquiryService.saveAnswer(request, admin))
			.isInstanceOf(CustomException.class)
			.hasMessage(InquiryErrorCode.NOT_FOUND_INQUIRY.getMessage());
	}

	@Test
	@DisplayName("이미 답변 완료 된 문의에 대한 답변은 등록할 수 없다.")
	void saveAnswerAlreadyRepliedException() {
		// given
		InquiryStatus status = InquiryStatus.REPLIED;
		Long inquiryId = inquiryRepository.save(InquiryFixture.createInquiry(status)).getId();
		String content = "문의 답변 내용";
		InquiryAnswerSaveRequest request = InquiryFixture.createInquiryAnswerSaveRequest(inquiryId, content);
		Admin admin = InquiryFixture.createAdmin();

		// when // then
		assertThatThrownBy(() -> inquiryService.saveAnswer(request, admin))
			.isInstanceOf(CustomException.class)
			.hasMessage(InquiryErrorCode.ALREADY_REPLIED.getMessage());
	}

	@Test
	@DisplayName("문의에 대한 답변을 수정할 수 있다.")
	void updateAnswer() {
		// given
		Inquiry inquiry = inquiryRepository.save(InquiryFixture.createInquiry());
		String content = "답변 내용";
		Long answerId = inquiryAnswerRepository.save(InquiryFixture.createInquiryAnswer(inquiry, content)).getId();
		String modifiedContent = "수정된 답변 내용";
		InquiryAnswerUpdateRequest request = InquiryFixture.createInquiryAnswerUpdateRequest(modifiedContent);

		// when
		InquiryAnswerUpdateResponse response = inquiryService.updateAnswer(answerId, request);

		// then
		assertAll(
			() -> assertThat(response.id()).isNotNull(),
			() -> assertThat(response.content()).isEqualTo(modifiedContent),
			() -> assertThat(response.createdAt()).isNotNull(),
			() -> assertThat(response.modifiedAt()).isAfter(response.createdAt())
		);
	}

	@Test
	@DisplayName("존재하지 않는 답변은 수정할 수 없다.")
	void updateAnswerNotExistException() {
		// given
		Long answerId = 0L;
		String modifiedContent = "수정된 답변 내용";
		InquiryAnswerUpdateRequest request = InquiryFixture.createInquiryAnswerUpdateRequest(modifiedContent);

		// when // then
		assertThatThrownBy(() -> inquiryService.updateAnswer(answerId, request))
			.isInstanceOf(CustomException.class)
			.hasMessage(InquiryErrorCode.NOT_FOUND_ANSWER.getMessage());
	}

	@Test
	@DisplayName("문의에 대한 답변을 삭제할 수 있다.")
	void deleteAnswer() {
		// given
		Admin admin = InquiryFixture.createAdmin();
		Inquiry inquiry = inquiryRepository.save(InquiryFixture.createInquiry());
		Long answerId = inquiryService.saveAnswer(
			InquiryFixture.createInquiryAnswerSaveRequest(inquiry.getId()), admin).id();

		// when
		inquiryService.deleteAnswer(answerId);
		Optional<Answer> foundAnswer = inquiryAnswerRepository.findById(answerId);

		// then
		assertAll(
			() -> assertThat(foundAnswer).isEmpty(),
			() -> assertThat(inquiry.getAnswer()).isNull(),
			() -> assertThat(inquiry.getStatus()).isEqualTo(InquiryStatus.WAITING)
		);
	}

	@Test
	@DisplayName("존재하지 않는 답변은 수정할 수 없다.")
	void deleteAnswerNotExistException() {
		// given
		Long answerId = 0L;

		// when // then
		assertThatThrownBy(() -> inquiryService.deleteAnswer(answerId))
			.isInstanceOf(CustomException.class)
			.hasMessage(InquiryErrorCode.NOT_FOUND_ANSWER.getMessage());
	}
}
