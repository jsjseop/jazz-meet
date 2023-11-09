import styled from '@emotion/styled';
import { useRef, useState } from 'react';
import { useVenueList } from '~/hooks/useVenueList';
import { Map } from './Map';
import { Panel } from './Panel';

export const MapPage: React.FC = () => {
  const mapRef = useRef<HTMLDivElement>(null);
  const [map, setMap] = useState<naver.maps.Map>();
  const venueListData = useVenueList(map);

  return (
    <StyledMapPage>
      <Map
        mapRef={mapRef}
        map={map}
        venueList={venueListData.venueList}
        onMapInitialized={(map: naver.maps.Map) => setMap(map)}
      />
      <Panel mapRef={mapRef} {...venueListData} />
    </StyledMapPage>
  );
};

const StyledMapPage = styled.div`
  overflow: hidden;
  height: calc(100vh - 73px);
  display: flex;
`;
