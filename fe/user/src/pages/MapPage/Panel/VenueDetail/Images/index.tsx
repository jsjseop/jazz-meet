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

  return (
    <>
      <PreviewImages
        images={images}
        onReturnButtonClick={onReturnButtonClick}
        onImageClick={() => setImageDetailOpen(true)}
      />
      {imageDetailOpen &&
        createPortal(
          <ImageDetail
            images={images}
            onClose={() => setImageDetailOpen(false)}
          />,
          document.body,
        )}
    </>
  );
};
