package kr.codesquad.jazzmeet.show.vo;

import lombok.Getter;

@Getter
public class ShowPoster {
	private Long id;
	private String url;

	public ShowPoster(Long id, String url) {
		this.id = id;
		this.url = url;
	}
}
