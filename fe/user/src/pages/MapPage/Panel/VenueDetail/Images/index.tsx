import { createPortal } from 'react-dom';
import { VenueDetailData } from '~/types/api.types';
import { ImageDetail } from './ImageDetail';
import { PreviewImages } from './PreviewImages';

type Props = { onReturnButtonClick: () => void } & Pick<
  VenueDetailData,
  'images'
>;

export const Images: React.FC<Props> = ({ images, onReturnButtonClick }) => {
  return (
    <>
      <PreviewImages
        images={images}
        onReturnButtonClick={onReturnButtonClick}
      />
      {createPortal(<ImageDetail />, document.body)}
    </>
  );
};
