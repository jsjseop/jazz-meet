import { Cards } from './CardList/Cards';
import { CardListHeader } from './CardList/CardListHeader';
import { CardList } from './CardList';
import { useEffect, useState } from 'react';
import { UpcomingShow } from 'types/api.types';
import { getUpcomingShows } from 'apis/show';
import { SwiperSlide } from 'swiper/react';
import { UpcomingShowCard } from './CardList/Cards/UpcomingShowCard';

export const UpcomingShows: React.FC = () => {
  const [upcomingShows, setUpcomingShows] = useState<UpcomingShow[]>();

  const updateAroundVenues = async () => {
    const aroundVenues = await getUpcomingShows();
    setUpcomingShows(aroundVenues);
  };

  useEffect(() => {
    updateAroundVenues();
  }, []);

  return (
    <CardList>
      <CardListHeader title="진행 중인 공연" />

      <Cards>
        {upcomingShows &&
          upcomingShows.map((upcomingShow) => (
            <SwiperSlide key={upcomingShow.showId}>
              <UpcomingShowCard upcomingShow={upcomingShow} />
            </SwiperSlide>
          ))}
      </Cards>
    </CardList>
  );
};
