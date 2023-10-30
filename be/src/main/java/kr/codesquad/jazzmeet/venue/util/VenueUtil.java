package kr.codesquad.jazzmeet.venue.util;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;

public final class VenueUtil {

	private VenueUtil() {
		throw new UnsupportedOperationException();
	}

	public static Point createPoint(Double latitude, Double longitude) {
		GeometryFactory geometryFactory = new GeometryFactory();
		// TODO: 잘못된 위치 정보인 경우 예외 처리
		return geometryFactory.createPoint(new Coordinate(longitude, latitude));
	}
}
