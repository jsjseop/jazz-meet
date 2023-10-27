import styled from '@emotion/styled';
import { VenueList } from './VenueList';
import { VenueDetail } from './VenueDetail';
import { useParams } from 'react-router-dom';

export const Panel: React.FC = () => {
  const { id } = useParams();

  return (
    <StyledPanel>
      <VenueList />
      {id && <VenueDetail />}
    </StyledPanel>
  );
};

const StyledPanel = styled.div`
  position: relative;
  width: 100%;
  height: 100%;
  display: flex;
`;
