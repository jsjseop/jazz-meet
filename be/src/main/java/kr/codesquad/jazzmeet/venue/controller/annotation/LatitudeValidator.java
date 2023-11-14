package kr.codesquad.jazzmeet.venue.controller.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class LatitudeValidator implements ConstraintValidator<Latitude, Double> {

	private static final Double MIN_LOW_LATITUDE = -90.0;
	private static final Double MAX_HIGH_LATITUDE = 90.0;

	@Override
	public boolean isValid(Double latitude, ConstraintValidatorContext context) {
		if (latitude == null) {
			return true;
		}

		return latitude >= MIN_LOW_LATITUDE && latitude <= MAX_HIGH_LATITUDE;
	}
}
