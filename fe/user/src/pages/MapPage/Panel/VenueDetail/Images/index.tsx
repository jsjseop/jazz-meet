import { useState } from 'react';
import { createPortal } from 'react-dom';
import { VenueDetailData } from '~/types/api.types';
import { ImageDetail } from './ImageDetail';
import { PreviewImages } from './PreviewImages';

type Props = { onReturnButtonClick: () => void } & Pick<
  VenueDetailData,
  'images'
>;

export const Images: React.FC<Props> = ({ images, onReturnButtonClick }) => {
  const [imageDetailOpen, setImageDetailOpen] = useState(false);
  const [imageIndex, setImageIndex] = useState(-1);

  const showImageDetail = (index: number) => {
    setImageIndex(index);
    setImageDetailOpen(true);
  };

  const hideImageDetail = () => {
    setImageIndex(-1);
    setImageDetailOpen(false);
  };

  return (
    <>
      <PreviewImages
        images={images}
        onReturnButtonClick={onReturnButtonClick}
        onImageClick={showImageDetail}
      />
      {imageDetailOpen &&
        createPortal(
          <ImageDetail
            currentIndex={imageIndex}
            images={images}
            onClose={hideImageDetail}
          />,
          document.body,
        )}
    </>
  );
};
