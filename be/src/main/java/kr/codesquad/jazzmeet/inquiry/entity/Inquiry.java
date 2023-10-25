package kr.codesquad.jazzmeet.inquiry.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Inquiry {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private String nickname;
	@Column(nullable = false)
	private String password;
	@Column(nullable = false)
	private String content;
	@Column(nullable = false)
	private String category;
	@Column(nullable = false)
	private String status;
	@Column(nullable = false)
	private LocalDateTime createdAt;
}
