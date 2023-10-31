package kr.codesquad.jazzmeet.venue.vo;

import java.util.List;

import org.locationtech.jts.geom.Point;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class VenueSearchData {
	private Long id;
	private String thumbnailUrl;
	private String name;
	private String address;
	private String description;
	private List<ShowInfoData> showInfoData;
	private Point location;

}
