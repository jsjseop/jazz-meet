package kr.codesquad.jazzmeet.venue.vo;

import org.locationtech.jts.geom.Point;

public class NearbyVenue {

	private Long id;
	private String name;
	private String address;
	private Point location;
	private String thumbnailUrl;

	public NearbyVenue(Long id, String name, String address, Point location, String thumbnailUrl) {
		this.id = id;
		this.name = name;
		this.address = address;
		this.location = location;
		this.thumbnailUrl = thumbnailUrl;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getAddress() {
		return address;
	}

	public Point getLocation() {
		return location;
	}

	public String getThumbnailUrl() {
		return thumbnailUrl;
	}
}
