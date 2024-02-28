package kr.codesquad.jazzmeet.venue.entity;

import java.util.ArrayList;
import java.util.List;

import org.locationtech.jts.geom.Point;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import kr.codesquad.jazzmeet.admin.entity.Admin;
import kr.codesquad.jazzmeet.image.entity.Image;
import kr.codesquad.jazzmeet.show.entity.Show;
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
	@Column(nullable = false, length = 50)
	private String name;
	@Column(nullable = false, length = 50)
	private String roadNameAddress; // 도로명
	@Column(nullable = false, length = 50)
	private String lotNumberAddress; // 지번
	@Column(length = 20)
	private String phoneNumber;
	@Column(length = 1000)
	private String description;
	@Column(nullable = false, columnDefinition = "point")
	private Point location;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "admin_id")
	private Admin admin;
	@Column(length = 500)
	private String thumbnailUrl;
	@OneToMany(mappedBy = "venue", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<VenueImage> images = new ArrayList<>();
	@OneToMany(mappedBy = "venue", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Link> links = new ArrayList<>();
	@OneToMany(mappedBy = "venue", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<VenueHour> venueHours = new ArrayList<>();
	@OneToMany(mappedBy = "venue", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<Show> shows = new ArrayList<>();

	@Builder
	public Venue(String name, String roadNameAddress, String lotNumberAddress, String phoneNumber, String description,
		Point location, Admin admin, String thumbnailUrl) {
		this.name = name;
		this.roadNameAddress = roadNameAddress;
		this.lotNumberAddress = lotNumberAddress;
		this.phoneNumber = phoneNumber;
		this.description = description;
		this.location = location;
		this.admin = admin;
		this.thumbnailUrl = thumbnailUrl;
	}

	public void updateVenue(String name, String roadNameAddress, String lotNumberAddress, String phoneNumber, String description,
		Point location, String thumbnailUrl) {
		this.name = name;
		this.roadNameAddress = roadNameAddress;
		this.lotNumberAddress = lotNumberAddress;
		this.phoneNumber = phoneNumber;
		this.description = description;
		this.location = location;
		this.thumbnailUrl = thumbnailUrl;
		this.links.clear();
		this.venueHours.clear();
		deleteImages();
		this.images.clear();
	}

	public void deleteImages() {
		for (VenueImage venueImage : images) {
			Image image = venueImage.getImage();
			image.delete();
		}
	}

	// 연관관계 편의 메서드
	public void addVenueImage(VenueImage venueImage) {
		this.images.add(venueImage);
		venueImage.addVenue(this);
	}

	public void addLink(Link link) {
		this.links.add(link);
		link.addVenue(this);
	}

	public void addVenueHour(VenueHour venueHour) {
		this.venueHours.add(venueHour);
		venueHour.addVenue(this);
	}
}
