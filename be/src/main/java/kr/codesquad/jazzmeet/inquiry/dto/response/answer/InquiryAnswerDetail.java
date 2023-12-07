package kr.codesquad.jazzmeet.inquiry.dto.response.answer;

import java.time.LocalDateTime;

public record InquiryAnswerDetail(
	Long id,
	String content,
	LocalDateTime createdAt,
	LocalDateTime modifiedAt
) {
	private boolean isAllFieldsNull() {
		return this.id == null && this.content == null && this.createdAt == null && this.modifiedAt == null;
	}

	public InquiryAnswerDetail checkAndSetToNull() {
		if (this.isAllFieldsNull()) {
			return null;
		}
		return this;
	}
}
