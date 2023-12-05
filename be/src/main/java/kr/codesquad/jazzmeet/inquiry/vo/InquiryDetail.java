package kr.codesquad.jazzmeet.inquiry.vo;

import java.time.LocalDateTime;

import kr.codesquad.jazzmeet.inquiry.util.InquiryStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class InquiryDetail {
	// inquiry
	private Long id;
	private InquiryStatus status;
	private String content;
	private String nickname;
	private LocalDateTime createdAt;
	// answer
	private Long answerId;
	private String answerContent;
	private LocalDateTime answerCreatedAt;
	private LocalDateTime answerModifiedAt;
}
