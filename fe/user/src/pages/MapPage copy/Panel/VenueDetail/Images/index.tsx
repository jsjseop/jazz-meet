// import { createPortal } from 'react-dom';
import { VenueDetailData } from '~/types/api.types';
import { PreviewImages } from './PreviewImages';
// import { ImageDetail } from './ImageDetail';

type Props = Pick<VenueDetailData, 'images'>;

export const Images: React.FC<Props> = ({ images }) => {
  return (
    <>
      <PreviewImages images={images} />
      {/* {createPortal(<ImageDetail />, document.body)} */}
    </>
  );
};
