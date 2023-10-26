import styled from '@emotion/styled';
import { Card } from './Card';
import 'swiper/css';
import 'swiper/css/navigation';
import { Navigation } from 'swiper/modules';
import { Swiper, SwiperSlide } from 'swiper/react';

type Props = {
  posters: {
    id: number;
    thumbnailUrl: string;
    name: string;
    address: string;
  }[];
};

export const Cards: React.FC<Props> = ({ posters }) => {
  return (
    <StyledCards>
      <Swiper
        navigation={true}
        modules={[Navigation]}
        breakpoints={{
          '480': {
            slidesPerView: 1,
            slidesPerGroup: 1,
            spaceBetween: 12,
          },
          '640': {
            slidesPerView: 2,
            slidesPerGroup: 2,
            spaceBetween: 12,
          },
          '960': {
            slidesPerView: 3,
            slidesPerGroup: 3,
            spaceBetween: 12,
          },
          '1200': {
            slidesPerView: 4,
            slidesPerGroup: 4,
            spaceBetween: 12,
          },
        }}
      >
        {posters.map((poster) => (
          <SwiperSlide key={poster.id}>
            <Card poster={poster} />
          </SwiperSlide>
        ))}
      </Swiper>
    </StyledCards>
  );
};

const StyledCards = styled.div`
  user-select: none;

  .swiper-button-prev,
  .swiper-button-next {
    color: #fff;
  }
`;
