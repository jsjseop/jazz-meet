import styled from '@emotion/styled';
import { VenueDetailData } from '~/types/api.types';

const PREVIEW_IMAGE_COUNT = 5;

type Props = Pick<VenueDetailData, 'images'>;

export const PreviewImages: React.FC<Props> = ({ images }) => {
  return (
    <StyledImages>
      {images.slice(0, PREVIEW_IMAGE_COUNT).map((image) => (
        <StyledImageWrapper key={image.id} imagesLength={images.length}>
          <StyledImage src={image.url} />
        </StyledImageWrapper>
      ))}
    </StyledImages>
  );
};

const StyledImages = styled.div`
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  grid-template-rows: repeat(2, 1fr);
  gap: 4px;
`;

const StyledImageWrapper = styled.div<{ imagesLength: number }>`
  position: relative;
  width: 100%;
  padding-top: 100%;

  &:first-of-type {
    grid-column: 1 / 3;
    grid-row: 1 / 3;
  }

  &:last-of-type {
    &::after {
      position: absolute;
      inset: 0;
      width: 100%;
      height: 100%;
      color: #fff;
      background: rgba(0, 0, 0, 0.3);
      content: ${({ imagesLength }) => {
        const count = imagesLength - PREVIEW_IMAGE_COUNT;

        return count > 0 ? `+${count}` : count;
      }}};
      cursor: pointer;
      display: flex;
      justify-content: center;
      align-items: center;
    }
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
