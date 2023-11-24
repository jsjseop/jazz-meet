// import { createPortal } from 'react-dom';
import { VenueDetailData } from '~/types/api.types';
import { PreviewImages } from './PreviewImages';
// import { ImageDetail } from './ImageDetail';

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
      {/* {createPortal(<ImageDetail />, document.body)} */}
    </>
  );
};
