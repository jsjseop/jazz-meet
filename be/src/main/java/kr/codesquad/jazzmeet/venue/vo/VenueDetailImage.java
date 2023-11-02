package kr.codesquad.jazzmeet.venue.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class VenueDetailImage {
	private Long id;
	private String url;

	public VenueDetailImage(Long id, String url) {
		this.id = id;
		this.url = url;
	}
}
