import styled from '@emotion/styled';
import ChevronLeftIcon from '@mui/icons-material/ChevronLeft';
import ChevronRightIcon from '@mui/icons-material/ChevronRight';
import CloseIcon from '@mui/icons-material/Close';
import { useEffect, useState } from 'react';
import { createPortal } from 'react-dom';
import { useOutletContext } from 'react-router-dom';
import SwiperCore from 'swiper';
import { Navigation } from 'swiper/modules';
import { Swiper, SwiperSlide } from 'swiper/react';
import { useShallow } from 'zustand/react/shallow';
import { useShowDetailStore } from '~/stores/useShowDetailStore';
import { ShowDetail as ShowDetailData } from '~/types/api.types';

type Props = {
  showList: ShowDetailData[];
};

export const ShowDetail: React.FC<Props> = ({ showList }) => {
  const mapElement = useOutletContext<React.RefObject<HTMLDivElement>>();
  const [swiper, setSwiper] = useState<SwiperCore>();
  const { showDetailId, setShowDetailId } = useShowDetailStore(
    useShallow((state) => ({
      showDetailId: state.showDetailId,
      setShowDetailId: state.setShowDetailId,
    })),
  );

  const currentSwiperIndex = showList.findIndex(
    (show) => show.id === showDetailId,
  );

  const onClickPrevButton = () => {
    const prevIndex = Math.max(currentSwiperIndex - 1, 0);
    setShowDetailId(showList[prevIndex].id);
  };

  const onClickNextButton = () => {
    const nextIndex = Math.min(currentSwiperIndex + 1, showList.length - 1);
    setShowDetailId(showList[nextIndex].id);
  };

  const onCloseButtonClick = () => setShowDetailId(0);

  // ShowDetail이 열린 상태에서(mount된 상태에서) 날짜가 바뀌면 첫번째 slide로 이동
  useEffect(() => {
    swiper?.slideTo(currentSwiperIndex, 0);
  }, [swiper, currentSwiperIndex]);

  return (
    <>
      {createPortal(
        <StyledShowDetail>
          <StyledShowDetailHeader>
            <CloseIcon
              sx={{
                width: '32px',
                height: '32px',
                fill: '#B5BEC6',
                '&:hover': { cursor: 'pointer', opacity: 0.7 },
              }}
              onClick={onCloseButtonClick}
            />
          </StyledShowDetailHeader>
          <StyledShowDetailBody>
            <Swiper
              modules={[Navigation]}
              navigation={{
                prevEl: '.swiper-prev',
                nextEl: '.swiper-next',
              }}
              onSwiper={setSwiper}
            >
              {showList.map((show, index) => (
                <SwiperSlide key={`${show.id}-${index}`}>
                  <StyledShowDetailContent>
                    <StyledShowDetailImage>
                      <img
                        src={show.posterUrl}
                        alt={`${show.teamName} 공연 포스터`}
                      />
                    </StyledShowDetailImage>
                    {show.description && (
                      <StyledShowDetailText>
                        {show.description}
                      </StyledShowDetailText>
                    )}
                  </StyledShowDetailContent>
                </SwiperSlide>
              ))}
            </Swiper>
            <StyledArrowButton
              className="swiper-prev"
              onClick={onClickPrevButton}
            >
              <ChevronLeftIcon sx={{ fill: '#B5BEC6' }} />
            </StyledArrowButton>
            <StyledArrowButton
              className="swiper-next"
              onClick={onClickNextButton}
            >
              <ChevronRightIcon sx={{ fill: '#B5BEC6' }} />
            </StyledArrowButton>
          </StyledShowDetailBody>
        </StyledShowDetail>,
        mapElement.current ?? document.body,
      )}
    </>
  );
};

const StyledShowDetail = styled.div`
  position: relative;
  z-index: 101;
  width: 100%;
  height: 100%;
  padding: 20px;
  box-sizing: border-box;
  background-color: #00000099;
`;

const StyledShowDetailHeader = styled.div`
  display: flex;
  align-items: center;
`;

const StyledShowDetailBody = styled.div`
  position: relative;
  height: 100%;
  display: flex;
  justify-content: center;

  & > .swiper {
    width: 80%;
  }

  .swiper-slide {
    display: flex;
    justify-content: center;
  }
`;

const StyledShowDetailContent = styled.div`
  max-width: 500px;
  height: 100%;
  overflow-y: auto;

  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 20px;

  &::-webkit-scrollbar {
    width: 18px;
  }

  &::-webkit-scrollbar-thumb {
    background-clip: padding-box;
    background-color: #c1c1c1;
    border-radius: 10px;
    border: 6px solid transparent;
  }

  &::-webkit-scrollbar-track {
    background-color: transparent;
  }
`;

const StyledShowDetailImage = styled.div`
  > img {
    width: 100%;
    object-fit: contain;
  }
`;

const StyledShowDetailText = styled.div`
  width: 100%;
  color: #c6c6c6;
  font-size: 20px;
  line-height: 130%;
  margin-bottom: 100px;
`;

const StyledArrowButton = styled.button`
  position: absolute;
  top: 40%;

  &:hover {
    cursor: pointer;
  }

  > svg {
    width: 64px;
    height: 64px;
  }

  &.swiper-prev {
    left: -3%;
  }

  &.swiper-next {
    right: -3%;
  }

  &.swiper-button-disabled {
    opacity: 0.2;
  }
`;
