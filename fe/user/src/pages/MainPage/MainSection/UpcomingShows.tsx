import { useEffect, useState } from 'react';
import { SwiperSlide } from 'swiper/react';
import { getUpcomingShows } from '~/apis/show';
import { UpcomingShow } from '~/types/api.types';
import { CardList } from './CardList';
import { CardListHeader } from './CardList/CardListHeader';
import { Cards } from './CardList/Cards';
import { UpcomingShowCard } from './CardList/Cards/UpcomingShowCard';

export const UpcomingShows: React.FC = () => {
  const [upcomingShows, setUpcomingShows] = useState<UpcomingShow[]>();

  useEffect(() => {
    const updateUpcomingShows = async () => {
      const upcomingShows = await getUpcomingShows();
      setUpcomingShows(upcomingShows);
    };

    updateUpcomingShows();
  }, []);

  return (
    <CardList>
      <CardListHeader title="다가오는 공연" />

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
