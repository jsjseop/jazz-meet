package kr.codesquad.jazzmeet.venue.util;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;

import kr.codesquad.jazzmeet.global.error.CustomException;
import kr.codesquad.jazzmeet.global.error.statuscode.LocationErrorCode;

public final class VenueUtil {

	private VenueUtil() {
		throw new UnsupportedOperationException();
	}

	public static Point createPoint(Double latitude, Double longitude) {
		GeometryFactory geometryFactory = new GeometryFactory();
		try {
			return geometryFactory.createPoint(new Coordinate(longitude, latitude));
		} catch (Exception e) {
			throw new CustomException(LocationErrorCode.INVALID_LOCATION_ERROR);
		}
	}

	// 좌표를 사용하여 사각형을 생성합니다.
	public static Polygon createRange(Double lowLatitude, Double highLatitude, Double lowLongitude,
		Double highLongitude) {
		GeometryFactory geometryFactory = new GeometryFactory();
		Coordinate[] coordinates = new Coordinate[] {
			new Coordinate(lowLongitude, lowLatitude),
			new Coordinate(highLongitude, lowLatitude),
			new Coordinate(highLongitude, highLatitude),
			new Coordinate(lowLongitude, highLatitude),
			new Coordinate(lowLongitude, lowLatitude)
		};

		return geometryFactory.createPolygon(coordinates);
	}
}
