import styled from '@emotion/styled';
import { useRef, useState } from 'react';
import { useVenueList } from '~/hooks/useVenueList';
import { Map } from './Map';
import { Panel } from './Panel';

export const MapPage: React.FC = () => {
  const [mapObj, setMapObj] = useState<naver.maps.Map>();
  const mapRef = useRef<HTMLDivElement>(null);
  const venueListData = useVenueList(mapObj);

  return (
    <StyledMapPage>
      <Map
        mapRef={mapRef}
        venueList={venueListData.venueList}
        onMapInitialized={(map: naver.maps.Map) => setMapObj(map)}
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
