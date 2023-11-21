package kr.codesquad.jazzmeet.image.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import kr.codesquad.jazzmeet.global.time.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Image extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false, length = 500)
	private String url;
	@Enumerated(value = EnumType.STRING)
	@Column(nullable = false, length = 12)
	private ImageStatus status;

	@Builder
	public Image(String url, ImageStatus status) {
		this.url = url;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public String getUrl() {
		return url;
	}

	public void delete() {
		this.status = ImageStatus.DELETED;
	}

	public void register() {
		this.status = ImageStatus.REGISTERED;
	}
}
