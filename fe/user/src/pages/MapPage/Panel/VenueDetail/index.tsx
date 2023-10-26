import styled from '@emotion/styled';
import { PreviewImages } from './PreviewImages';
import { Header } from './Header';
import { BasicInfo } from './BasicInfo';
import { RestInfo } from './RestInfo';
import { Calendar } from './Calendar';

export const VenueDetail: React.FC = () => {
  return (
    <StyledVenueDetail>
      <PreviewImages />
      <Header />
      <BasicInfo />
      <RestInfo />
      <Calendar />

      {/* {showInfoDetail && <InfoDetail />} */}
      {/* {showInfoDetail && createPortal(<Images />)} */}
    </StyledVenueDetail>
  );
};

const StyledVenueDetail = styled.div``;
