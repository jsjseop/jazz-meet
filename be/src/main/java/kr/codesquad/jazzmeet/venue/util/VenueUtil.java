package kr.codesquad.jazzmeet.venue.util;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateSequence;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.impl.CoordinateArraySequence;

public class VenueUtil {

	public static Point createPoint(Double latitude, Double longitude) {
		Coordinate[] coordinates = new Coordinate[] {
			new Coordinate(latitude, longitude)};
		CoordinateSequence coordinateSequence = new CoordinateArraySequence(coordinates);
		GeometryFactory geometryFactory = new GeometryFactory();
		return geometryFactory.createPoint(coordinateSequence);
	}
}
