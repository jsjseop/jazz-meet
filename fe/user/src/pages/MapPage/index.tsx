import styled from '@emotion/styled';
import { useEffect, useRef, useState } from 'react';
import { Map } from './Map';
import { Panel } from './Panel';
import { getVenuePinsByMapBounds, getVenuesByMapBounds } from '~/apis/venue';
import { Pin, SearchedVenues } from '~/types/api.types';
import { getMapBounds } from '~/utils/map';

export const MapPage: React.FC = () => {
  const mapElement = useRef<HTMLDivElement>(null);
  const [map, setMap] = useState<naver.maps.Map>();

  const [pins, setPins] = useState<Pin[]>();
  const [venueList, setVenueList] = useState<SearchedVenues>();

  // 지도가 첫 렌더링 될 때
  useEffect(() => {
    if (!map) {
      return;
    }

    const bounds = getMapBounds(map);

    (async () => {
      const [pins, venueList] = await Promise.all([
        getVenuePinsByMapBounds(bounds),
        getVenuesByMapBounds(bounds),
      ]);

      setPins(pins);
      setVenueList(venueList);
    })();
  }, [map]);

  return (
    <StyledMapPage>
      <Map
        mapElement={mapElement}
        map={map}
        onMapInitialized={(map: naver.maps.Map) => setMap(map)}
      />
      <Panel mapElement={mapElement} />
    </StyledMapPage>
  );
};

const StyledMapPage = styled.div`
  overflow: hidden;
  height: calc(100vh - 73px);
  display: flex;
`;
