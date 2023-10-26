import styled from '@emotion/styled';
import 'swiper/css';
import 'swiper/css/pagination';
import { Pagination } from 'swiper/modules';
import { Swiper, SwiperSlide } from 'swiper/react';

type Props = {
  imageUrls: string[];
  description?: string;
};

export const SwiperComponent: React.FC<Props> = ({
  imageUrls,
  description,
}) => {
  return (
    <StyledCarousel>
      <Swiper modules={[Pagination]} pagination={{ clickable: true }}>
        {imageUrls.map((imageUrl, index) => (
          <SwiperSlide key={`${imageUrl}-${index}`}>
            <SlideImage src={imageUrl} alt={description} />
          </SwiperSlide>
        ))}
      </Swiper>
    </StyledCarousel>
  );
};

const StyledCarousel = styled.div`
  width: 100%;
  user-select: none;

  .swiper {
    width: 100%;
    height: 100%;
  }

  .swiper-slide {
    width: 100%;
    height: 100%;
  }

  .swiper-pagination {
    left: -30%;
    bottom: 30px;
  }

  .swiper-pagination-bullet {
    background-color: #fff;
  }
`;

const SlideImage = styled.img`
  width: 100%;
  height: 498px;
  object-fit: cover;
`;
