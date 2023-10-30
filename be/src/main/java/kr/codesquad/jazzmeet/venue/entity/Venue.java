package kr.codesquad.jazzmeet.venue.entity;

import org.locationtech.jts.geom.Point;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Venue {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private String roadNameAddress; // 도로명
	@Column(nullable = false)
	private String lotNumberAddress; // 지번
	private String phoneNumber;
	private String description;
	@Column(nullable = false, columnDefinition = "point")
	private Point location;
	private Long adminId;
	private String thumbnailUrl;
	@Embedded
	private Images images;
	@Embedded
	private Links links;

	@Builder
	public Venue(String name, String roadNameAddress, String lotNumberAddress, Point location, String thumbnailUrl) {
		this.name = name;
		this.roadNameAddress = roadNameAddress;
		this.lotNumberAddress = lotNumberAddress;
		this.location = location;
		this.thumbnailUrl = thumbnailUrl;
	}
}
