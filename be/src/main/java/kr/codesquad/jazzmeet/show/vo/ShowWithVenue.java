package kr.codesquad.jazzmeet.show.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ShowWithVenue {
	private Long id;
	private String name;
	@JsonIgnore
	private String cityAndDistrict;
	private List<ShowSummary> shows;

	public ShowWithVenue(Long id, String name, String cityAndDistrict, List<ShowSummary> shows) {
		this.id = id;
		this.name = name;
		this.cityAndDistrict = cityAndDistrict;
		this.shows = shows;
	}
}
