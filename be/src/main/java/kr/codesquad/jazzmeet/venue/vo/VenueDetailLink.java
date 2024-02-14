package kr.codesquad.jazzmeet.venue.vo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class VenueDetailLink {
	private String type;
	private String url;

	public VenueDetailLink(String type, String url) {
		this.type = type;
		this.url = url;
	}
}
