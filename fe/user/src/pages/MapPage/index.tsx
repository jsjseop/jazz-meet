import styled from '@emotion/styled';
import { useVenueList } from '@hooks/useVenueList';
import { useRef } from 'react';
import { Map } from './Map';
import { Panel } from './Panel';

export const MapPage: React.FC = () => {
  const venueListData = useVenueList();
  const mapRef = useRef<HTMLDivElement>(null);
  
  return (
    <StyledMapPage>
      <Map mapRef={mapRef} />
      <Panel mapRef={mapRef} {...venueListData} />
    </StyledMapPage>
  );
};

const StyledMapPage = styled.div`
  height: calc(100vh - 73px);
  display: flex;
`;
