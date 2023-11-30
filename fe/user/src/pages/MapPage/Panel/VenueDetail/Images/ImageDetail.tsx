import styled from '@emotion/styled';
import CloseIcon from '@mui/icons-material/Close';
import 'swiper/css';
import 'swiper/css/navigation';
import { Navigation, Pagination } from 'swiper/modules';
import { Swiper, SwiperSlide } from 'swiper/react';
import { IMAGE_DETAIL_MODAL_Z_INDEX } from '~/constants/Z_INDEX';
import { VenueDetailData } from '~/types/api.types';

type Props = {
  onClose: () => void;
} & Pick<VenueDetailData, 'images'>;

export const ImageDetail: React.FC<Props> = ({ images, onClose }) => {
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
          pagination={{
            type: 'fraction',
          }}
          navigation={true}
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

  .swiper-button-prev,
  .swiper-button-next {
    position: absolute;
    color: #ffffff !important;
  }
`;

const SlideImage = styled.img`
  width: 100%;
  height: 100%;
  object-fit: contain;
`;
