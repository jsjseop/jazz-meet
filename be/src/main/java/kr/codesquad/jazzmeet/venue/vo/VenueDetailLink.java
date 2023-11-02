package kr.codesquad.jazzmeet.venue.vo;

import kr.codesquad.jazzmeet.venue.entity.LinkType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class VenueDetailLink {
	private String type;
	private String url;

	public VenueDetailLink(LinkType type, String url) {
		this.type = type.getName();
		this.url = url;
	}
}
