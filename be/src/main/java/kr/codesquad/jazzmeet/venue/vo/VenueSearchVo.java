package kr.codesquad.jazzmeet.venue.vo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VenueSearchVo {
	private List<VenueSearchData> venues;
	private int venueCount;
	private int currentPage;
	private int maxPage;
}
