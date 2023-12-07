import styled from '@emotion/styled';
import CloseIcon from '@mui/icons-material/Close';
import { useEffect, useState } from 'react';
import SwiperCore from 'swiper';
import 'swiper/css';
import 'swiper/css/navigation';
import { Navigation, Pagination } from 'swiper/modules';
import { Swiper, SwiperSlide } from 'swiper/react';
import CaretLeft from '~/assets/icons/CaretLeft.svg?react';
import CaretRight from '~/assets/icons/CaretRight.svg?react';
import { IMAGE_DETAIL_MODAL_Z_INDEX } from '~/constants/Z_INDEX';
import { VenueDetailData } from '~/types/api.types';

type Props = {
  currentIndex: number;
  onClose: () => void;
} & Pick<VenueDetailData, 'images'>;

export const ImageDetail: React.FC<Props> = ({
  currentIndex,
  images,
  onClose,
}) => {
  const [swiper, setSwiper] = useState<SwiperCore>();

  useEffect(() => {
    swiper?.slideTo(currentIndex, 0);
  }, [currentIndex, swiper]);

  return (
    <StyledImageDetail>
      <StyledImageDetailHeader>
        <CloseIcon
          sx={{
            width: '64px',
            height: '64px',
            fill: '#B5BEC6',
            '&:hover': {
              cursor: 'pointer',
              opacity: '0.7',
            },
          }}
          onClick={onClose}
        />
      </StyledImageDetailHeader>
      <StyledImageDetailContent>
        <Swiper
          modules={[Pagination, Navigation]}
          onSwiper={setSwiper}
          pagination={{
            type: 'fraction',
          }}
          navigation={{
            prevEl: '.swiper-prev',
            nextEl: '.swiper-next',
          }}
        >
          {images.map((image, index) => (
            <SwiperSlide key={`${image.url}-${index}`}>
              <SlideImage
                src={image.url}
                alt={`${index + 1}번째 공연장 이미지`}
              />
            </SwiperSlide>
          ))}
        </Swiper>
        <StyledArrowButton className="swiper-prev">
          <CaretLeft />
        </StyledArrowButton>
        <StyledArrowButton className="swiper-next">
          <CaretRight />
        </StyledArrowButton>
      </StyledImageDetailContent>
    </StyledImageDetail>
  );
};

const StyledImageDetail = styled.div`
  position: fixed;
  z-index: ${IMAGE_DETAIL_MODAL_Z_INDEX};
  top: 73px;
  width: 100vw;
  height: calc(100vh - 73px);
  background-color: #00000099;
`;

const StyledImageDetailHeader = styled.div`
  position: absolute;
  top: 20px;
  left: 20px;
`;

const StyledImageDetailContent = styled.div`
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 100px;
  box-sizing: border-box;

  user-select: none;

  .swiper {
    width: 100%;
    height: 100%;
  }

  .swiper-slide {
    width: 100%;
    height: 100%;
  }

  .swiper-pagination-fraction {
    color: #ffffff;
  }
`;

const SlideImage = styled.img`
  width: 100%;
  height: 100%;
  object-fit: contain;
`;

const StyledArrowButton = styled.button`
  position: absolute;
  top: 45%;

  &:hover {
    cursor: pointer;
  }

  > svg {
    width: 60px;
    height: 88px;
  }

  &.swiper-prev {
    left: 20px;
  }

  &.swiper-next {
    right: 20px;
  }

  &.swiper-button-disabled {
    opacity: 0.2;
  }
`;
