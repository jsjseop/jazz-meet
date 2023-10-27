import styled from '@emotion/styled';
import { VenueList } from './VenueList';
import { VenueDetail } from './VenueDetail';

export const Panel: React.FC = () => {
  return (
    <StyledPanel>
      <VenueList />
      <VenueDetail />
    </StyledPanel>
  );
};

const StyledPanel = styled.div`
  width: 100%;
  height: 100%;
  overflow: scroll;
`;
