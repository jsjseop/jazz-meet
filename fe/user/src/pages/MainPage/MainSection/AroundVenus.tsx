import { useEffect, useState } from 'react';
import { SwiperSlide } from 'swiper/react';
import { getAroundVenues } from '~/apis/venue';
import { BASIC_COORDINATE } from '~/constants/MAP';
import { useUserCoordinate } from '~/hooks/useUserCoordinate';
import { AroundVenue } from '~/types/api.types';
import { CardList } from './CardList';
import { CardListHeader } from './CardList/CardListHeader';
import { CardListSkeleton } from './CardList/CardListSkeleton';
import { Cards } from './CardList/Cards';
import { AroundVenueCard } from './CardList/Cards/AroundVenueCard';

export const AroundVenus: React.FC = () => {
  const { userCoordinate } = useUserCoordinate();
  const [aroundVenues, setAroundVenues] = useState<AroundVenue[]>();
  const [isLoading, setIsLoading] = useState(false);

  useEffect(() => {
    setIsLoading(false);
    const updateAroundVenues = async () => {
      const aroundVenues = await getAroundVenues(
        userCoordinate ?? BASIC_COORDINATE,
      );
      setAroundVenues(aroundVenues);
      setIsLoading(true);
    };

    updateAroundVenues();
  }, [userCoordinate]);

  return (
    <CardList>
      <CardListHeader title="주변 공연장" />

      <Cards>
        {isLoading ? (
          aroundVenues?.map((aroundVenue) => (
            <SwiperSlide key={aroundVenue.id}>
              <AroundVenueCard aroundVenue={aroundVenue} />
            </SwiperSlide>
          ))
        ) : (
          <CardListSkeleton />
        )}
      </Cards>
    </CardList>
  );
};
