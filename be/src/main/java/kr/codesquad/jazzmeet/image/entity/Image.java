package kr.codesquad.jazzmeet.image.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Image {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false, length = 500)
	private String url;
	@Enumerated(value = EnumType.STRING)
	@Column(nullable = false, length = 12)
	private ImageStatus status;
	@Column(nullable = false)
	private LocalDateTime createdAt;

	@Builder
	public Image(String url, ImageStatus status, LocalDateTime createdAt) {
		this.url = url;
		this.status = status;
		this.createdAt = createdAt;
	}

	public Long getId() {
		return id;
	}

	public String getUrl() {
		return url;
	}

	public void updateStatus(ImageStatus status) {
		this.status = status;
	}
}
