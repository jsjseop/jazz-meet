import styled from '@emotion/styled';
import { VenueList } from './VenueList';
import { Outlet } from 'react-router-dom';

type Props = {
  mapRef: React.RefObject<HTMLDivElement>;
};

export const Panel: React.FC<Props> = ({ mapRef }) => {
  return (
    <StyledPanel>
      <VenueList />
      <Outlet context={mapRef} />
    </StyledPanel>
  );
};

const StyledPanel = styled.div`
  position: relative;
  width: 100%;
  height: 100%;
  display: flex;
`;
