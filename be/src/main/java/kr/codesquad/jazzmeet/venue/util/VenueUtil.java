package kr.codesquad.jazzmeet.venue.util;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;

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
}
