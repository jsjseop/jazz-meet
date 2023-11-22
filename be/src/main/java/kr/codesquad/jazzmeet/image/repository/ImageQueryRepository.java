package kr.codesquad.jazzmeet.image.repository;

import static kr.codesquad.jazzmeet.image.entity.QImage.*;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import kr.codesquad.jazzmeet.image.entity.Image;
import kr.codesquad.jazzmeet.image.entity.ImageStatus;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class ImageQueryRepository {

	private final JPAQueryFactory query;

	public List<Image> findAllNotRegistered(LocalDate date) {
		return query.selectFrom(image)
			.where(image.status.eq(ImageStatus.DELETED)
				.or(image.status.eq(ImageStatus.UNREGISTERED)
					.and(isDifferentDate(date))
				)
			)
			.fetch();
	}

	private BooleanExpression isDifferentDate(LocalDate date) {
		return image.createdAt.dayOfYear().ne(date.getDayOfYear());
	}

	public void deleteAllInUrls(List<String> urls) {
		query.delete(image)
			.where(image.url.in(urls))
			.execute();
	}
}
