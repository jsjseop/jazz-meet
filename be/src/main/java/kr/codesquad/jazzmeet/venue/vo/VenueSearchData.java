package kr.codesquad.jazzmeet.venue.vo;

import java.util.List;

import org.locationtech.jts.geom.Point;

import kr.codesquad.jazzmeet.venue.dto.ShowInfo;
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
	private String address; // 도로명 주소
	private String description;
	private List<ShowInfo> showInfo;
	private Point location;
}
