import styled from '@emotion/styled';
import { useEffect, useRef, useState } from 'react';
import { useLocation } from 'react-router-dom';
import { useMapDataUpdater } from '~/hooks/useMapDataUpdater';
import { Map } from './Map';
import { Panel } from './Panel';

export const MapPage: React.FC = () => {
  const [map, setMap] = useState<naver.maps.Map>();
  const {
    searchedVenues,
    updateMapDataBasedOnBounds,
    updateMapDataBySearch,
    updateMapDataByVenueId,
    handleChangeVenueListPage,
  } = useMapDataUpdater(map);
  const mapElement = useRef<HTMLDivElement>(null);

  const { search } = useLocation();
  const word = new URLSearchParams(search).get('word');
  const venueId = new URLSearchParams(search).get('venueId');

  useEffect(() => {
    if (word) {
      updateMapDataBySearch(word);

      return;
    }

    if (venueId) {
      updateMapDataByVenueId(venueId);

      return;
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [word, venueId]);

  return (
    <StyledMapPage>
      <Map
        mapElement={mapElement}
        onMapInitialized={(map: naver.maps.Map) => setMap(map)}
        onCurrentViewSearchClick={updateMapDataBasedOnBounds}
      />
      <Panel
        mapElement={mapElement}
        searchedVenus={searchedVenues}
        handleChangeVenueListPage={handleChangeVenueListPage}
      />
    </StyledMapPage>
  );
};

const StyledMapPage = styled.div`
  overflow: hidden;
  height: calc(100vh - 73px);
  display: flex;
`;
