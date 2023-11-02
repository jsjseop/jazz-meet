package kr.codesquad.jazzmeet.image.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Image {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Getter
	@Column(nullable = false, length = 500)
	private String url;
	@Column(nullable = false, length = 10)
	private String status;
	@Column(nullable = false)
	private LocalDateTime createdAt;

	@Builder
	public Image(String url, String status, LocalDateTime createdAt) {
		this.url = url;
		this.status = status;
		this.createdAt = createdAt;
	}
}
