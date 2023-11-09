package kr.codesquad.jazzmeet.inquiry.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import kr.codesquad.jazzmeet.inquiry.util.InquiryCategory;
import kr.codesquad.jazzmeet.inquiry.util.InquiryStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Inquiry {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Getter
	@Column(nullable = false, length = 20)
	private String nickname;
	@Column(nullable = false, length = 200)
	private String password;
	@Getter
	@Column(nullable = false, length = 500)
	private String content;
	@Enumerated(value = EnumType.STRING)
	@Column(nullable = false, length = 10)
	private InquiryCategory category;
	@Enumerated(value = EnumType.STRING)
	@Column(nullable = false, length = 10)
	private InquiryStatus status;
	@CreationTimestamp
	@Column(nullable = false)
	private LocalDateTime createdAt;
	@OneToOne(mappedBy = "inquiry", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private Answer answer;

	@Builder
	public Inquiry(String nickname, String password, String content, InquiryCategory category,
		InquiryStatus status, LocalDateTime createdAt) {
		this.nickname = nickname;
		this.password = password;
		this.content = content;
		this.category = category;
		this.status = status;
		this.createdAt = createdAt;
	}

	@PrePersist
	public void PrePersist() { // DB에 default 값 넣어주는 역할
		if (this.status == null) {
			this.status = InquiryStatus.WAITING;
		}
	}
}
