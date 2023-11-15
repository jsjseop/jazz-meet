package kr.codesquad.jazzmeet.image.repository;

import static kr.codesquad.jazzmeet.image.entity.QImage.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class ImageQueryRepository {

	private final JPAQueryFactory query;

	public void deleteAllInUrls(List<String> urls) {
		query.delete(image)
			.where(image.url.in(urls))
			.execute();
	}
}
