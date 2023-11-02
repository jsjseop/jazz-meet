import { Cards } from './CardList/Cards';
import { CardListHeader } from './CardList/CardListHeader';
import { CardList } from './CardList';
import { useEffect, useState } from 'react';
import { SwiperSlide } from 'swiper/react';
import { UpcomingShowCard } from './CardList/Cards/UpcomingShowCard';
import { UpcomingShow } from '~/types/api.types';
import { getUpcomingShows } from '~/apis/show';

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
