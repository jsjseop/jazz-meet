package kr.codesquad.jazzmeet.venue.controller.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class LongitudeValidator implements ConstraintValidator<Longitude, Double> {

	private static final Double MIN_LOW_LONGITUDE = -180.0;
	private static final Double MAX_HIGH_LONGITUDE = 180.0;

	@Override
	public boolean isValid(Double longitude, ConstraintValidatorContext context) {
		if (longitude == null) {
			return true;
		}

		return longitude >= MIN_LOW_LONGITUDE && longitude <= MAX_HIGH_LONGITUDE;
	}
}
