import styled from '@emotion/styled';
import { PreviewImages } from './PreviewImages';

export const VenueDetail: React.FC = () => {
  return (
    <StyledVenueDetail>
      <PreviewImages />

      {/* <Header /> */}
      {/* <BasicInfo /> */}
      {/* <RestInfo /> */}
      {/* {showInfoDetail && <InfoDetail />} */}
      {/* {showInfoDetail && createPortal(<Images />)} */}
    </StyledVenueDetail>
  );
};

const StyledVenueDetail = styled.div``;
