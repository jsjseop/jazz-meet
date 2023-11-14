package kr.codesquad.jazzmeet.venue.controller.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import kr.codesquad.jazzmeet.venue.dto.request.RangeCoordinatesRequest;

public class RangeCoordinatesValidator implements
	ConstraintValidator<RangeCoordinates, RangeCoordinatesRequest> {

	private static final Double MIN_LOW_LATITUDE = -90.0;
	private static final Double MAX_HIGH_LATITUDE = 90.0;
	private static final Double MIN_LOW_LONGITUDE = -180.0;
	private static final Double MAX_HIGH_LONGITUDE = 180.0;

	@Override
	public boolean isValid(RangeCoordinatesRequest value, ConstraintValidatorContext context) {
		Double lowLatitude = value.lowLatitude();
		Double highLatitude = value.highLatitude();
		Double lowLongitude = value.lowLongitude();
		Double highLongitude = value.highLongitude();

		return value.hasNull() || (lowLatitude >= MIN_LOW_LATITUDE && highLatitude <= MAX_HIGH_LATITUDE
			&& lowLongitude >= MIN_LOW_LONGITUDE && highLongitude <= MAX_HIGH_LONGITUDE);
	}
}
