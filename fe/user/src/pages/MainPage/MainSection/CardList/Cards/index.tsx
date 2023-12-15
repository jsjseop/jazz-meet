import styled from '@emotion/styled';
import { Children, useId } from 'react';
import 'swiper/css';
import 'swiper/css/navigation';
import { Navigation } from 'swiper/modules';
import { Swiper } from 'swiper/react';
import CaretLeft from '~/assets/icons/CaretLeft.svg?react';
import CaretRight from '~/assets/icons/CaretRight.svg?react';

type Props = {
  children: React.ReactNode;
};

export const Cards: React.FC<Props> = ({ children }) => {
  const cardsId = useId().split(':')[1];

  return (
    <StyledCards>
      {Children.count(children) > 0 ? (
        <>
          <Swiper
            navigation={{
              prevEl: `.swiper-prev.${cardsId}`,
              nextEl: `.swiper-next.${cardsId}`,
            }}
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
            {Children.toArray(children)}
          </Swiper>
          <StyledArrowButton className={`swiper-prev ${cardsId}`}>
            <CaretLeft />
          </StyledArrowButton>
          <StyledArrowButton className={`swiper-next ${cardsId}`}>
            <CaretRight />
          </StyledArrowButton>
        </>
      ) : (
        <StyledEmptyList>목록이 없습니다.</StyledEmptyList>
      )}
    </StyledCards>
  );
};

const StyledCards = styled.div`
  user-select: none;
  position: relative;
`;

const StyledArrowButton = styled.button`
  position: absolute;
  top: 30%;

  &:hover {
    cursor: pointer;
  }

  > svg {
    width: 60px;
    height: 88px;
  }

  &.swiper-prev {
    left: -60px;
  }

  &.swiper-next {
    right: -60px;
  }

  &.swiper-button-disabled {
    opacity: 0.2;
  }
`;

const StyledEmptyList = styled.div`
  min-height: 200px;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 36px;
  font-weight: bold;
  color: #c4c4c4;
`;
