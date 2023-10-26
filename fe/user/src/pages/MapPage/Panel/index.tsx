import styled from '@emotion/styled';
import { VenueList } from './VenueList';

export const Panel: React.FC = () => {
  return (<StyledPanel>
    
    <VenueList />
      
    {/* <VenueDetail>
      <ImageGrid />
      <Header />
      <BasicInfo />
      <RestInfo />
      { showInfoDetail && <InfoDetail /> }
      { showInfoDetail && createPortal(<Images/> )}
    </VenueDetail> */}
  </StyledPanel>)
};

const StyledPanel = styled.div`
  width: 100%;
  height: inherit;
`;
