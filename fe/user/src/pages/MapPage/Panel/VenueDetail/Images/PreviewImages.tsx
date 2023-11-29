import styled from '@emotion/styled';
import { IconButton } from '@mui/material';
import CaretLeft from '~/assets/icons/CaretLeft.svg?react';
import { VenueDetailData } from '~/types/api.types';

const PREVIEW_IMAGE_COUNT = 5;

type Props = { onReturnButtonClick: () => void } & Pick<
  VenueDetailData,
  'images'
>;

export const PreviewImages: React.FC<Props> = ({
  images,
  onReturnButtonClick,
}) => {
  return (
    <StyledImages>
      {images.slice(0, PREVIEW_IMAGE_COUNT).map((image, index) => (
        <StyledImageWrapper key={image.id}>
          <StyledImage src={image.url} />
          {index === PREVIEW_IMAGE_COUNT - 1 && (
            <StyledMoreImagesButton
              onClick={() => console.log('이미지 더보기')}
            >
              더보기
            </StyledMoreImagesButton>
          )}
        </StyledImageWrapper>
      ))}
      <IconButton
        type="button"
        sx={{ position: 'absolute' }}
        onClick={onReturnButtonClick}
      >
        <CaretLeft />
      </IconButton>
    </StyledImages>
  );
};

const StyledImages = styled.div`
  position: relative;
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  grid-template-rows: repeat(2, 1fr);
  gap: 4px;
`;

const StyledImageWrapper = styled.div`
  position: relative;
  width: 100%;
  padding-top: 100%;

  &:first-of-type {
    grid-column: 1 / 3;
    grid-row: 1 / 3;
  }
`;

const StyledImage = styled.img`
  position: absolute;
  inset: 0;
  width: 100%;
  height: 100%;
  object-fit: cover;
  cursor: pointer;

  &:hover {
    opacity: 0.7;
  }
`;

const StyledMoreImagesButton = styled.button`
  position: absolute;
  inset: 0;
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 28px;
  color: #fff;
  background: rgba(0, 0, 0, 0.3);
  cursor: pointer;
`;
