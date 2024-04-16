package kr.codesquad.jazzmeet.show.entity;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import kr.codesquad.jazzmeet.admin.entity.Admin;
import kr.codesquad.jazzmeet.image.entity.Image;
import kr.codesquad.jazzmeet.venue.entity.Venue;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "`show`")
public class Show {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false, length = 50)
	private String teamName;
	@Column(length = 1000)
	private String description;
	@Column(nullable = false)
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "venue_id")
	private Venue venue;
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "poster_id")
	private Image poster;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "admin_id")
	private Admin admin;

	@Builder
	public Show(String teamName, String description, LocalDateTime startTime, LocalDateTime endTime, Venue venue,
		Image poster, Admin admin) {
		this.teamName = teamName;
		this.description = description;
		this.startTime = startTime;
		this.endTime = endTime;
		this.venue = venue;
		this.poster = poster;
		this.admin = admin;
	}

	public void updateShow(String teamName, String description, LocalDateTime startTime, LocalDateTime endTime,
		Image poster) {
		this.teamName = teamName;
		this.description = description;
		this.startTime = startTime;
		this.endTime = endTime;
		this.poster = poster;
		registerPoster();
	}

	private void registerPoster() {
		this.poster.register();
	}

	public void deletePoster() {
		this.poster.delete();
	}
}
