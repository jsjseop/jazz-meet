package kr.codesquad.jazzmeet.show.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.codesquad.jazzmeet.show.entity.Show;

@Repository
public interface ShowRepository extends JpaRepository<Show, Long> {

	List<Show> findTop10BystartTimeGreaterThanOrEndTimeGreaterThanOrderByStartTime(LocalDateTime BeforeTime,
		LocalDateTime upcomingTime);
}
