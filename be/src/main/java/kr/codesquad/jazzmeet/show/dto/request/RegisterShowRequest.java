package kr.codesquad.jazzmeet.show.dto.request;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record RegisterShowRequest(
	@NotNull(message = "공연 명을 입력해주세요.")
	@Size(max = 50, message = "공연 명은 50자까지 가능합니다.")
	String name,
	@Size(max = 1000, message = "설명은 1000자까지 입력 가능합니다.")
	String description,
	@NotNull(message = "poster id를 입력해주세요.")
	Long posterId,
	LocalDateTime startTime,
	LocalDateTime endTime
) {
}
