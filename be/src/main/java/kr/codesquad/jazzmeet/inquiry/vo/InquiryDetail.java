package kr.codesquad.jazzmeet.inquiry.vo;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class InquiryDetail {
	private Long inquiryId;
	private String inquiryContent;
	// answer
	private Long id;
	private String content;
	private LocalDateTime createdAt;
	private LocalDateTime modifiedAt;
}
