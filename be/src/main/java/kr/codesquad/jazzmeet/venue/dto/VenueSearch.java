package kr.codesquad.jazzmeet.venue.dto;

import java.util.List;

public record VenueSearch(
	Long id,
	String thumbnailUrl,
	String name,
	String address, // 도로명 주소
	String description,
	List<ShowInfo> showInfo,
	Double latitude,
	Double longitude) {
}
