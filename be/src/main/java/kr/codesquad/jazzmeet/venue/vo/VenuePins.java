package kr.codesquad.jazzmeet.venue.vo;

import org.locationtech.jts.geom.Point;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class VenuePins {
	private Long id;
	private String name;
	private Point location;

	public VenuePins(Long id, String name, Point location) {
		this.id = id;
		this.name = name;
		this.location = location;
	}
}
