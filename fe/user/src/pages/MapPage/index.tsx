import styled from '@emotion/styled';
import { useRef } from 'react';
import { useVenueList } from '~/hooks/useVenueList';
import { Map } from './Map';
import { Panel } from './Panel';

export const MapPage: React.FC = () => {
  const venueListData = useVenueList();
  const mapRef = useRef<HTMLDivElement>(null);

  return (
    <StyledMapPage>
      <Map mapRef={mapRef} venueList={venueListData.venueList} />
      <Panel mapRef={mapRef} {...venueListData} />
    </StyledMapPage>
  );
};

const StyledMapPage = styled.div`
  overflow: hidden;
  height: calc(100vh - 73px);
  display: flex;
`;
