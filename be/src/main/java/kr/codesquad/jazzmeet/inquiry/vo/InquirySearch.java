package kr.codesquad.jazzmeet.inquiry.vo;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class InquirySearch {
	private Long id;
	private String status;
	private String content;
	private String nickname;
	private LocalDateTime createdAt;
}
