import styled from '@emotion/styled';
import { useEffect, useRef, useState } from 'react';
import { useLocation } from 'react-router-dom';
import { useMapDataUpdater } from '~/hooks/useMapDataUpdater';
import { Map } from './Map';
import { Panel } from './Panel';

export const MapPage: React.FC = () => {
  const mapElement = useRef<HTMLDivElement>(null);
  const [map, setMap] = useState<naver.maps.Map>();
  const {
    searchedVenus,
    updateMapDataBasedOnBounds,
    updateMapDataBySearch,
    handleChangeVenueListPage,
  } = useMapDataUpdater(map);

  const { search } = useLocation();
  const word = new URLSearchParams(search).get('word');

  // 지도가 첫 렌더링 될 때
  useEffect(() => {
    if (word) {
      updateMapDataBySearch(word);

      return;
    }

    updateMapDataBasedOnBounds();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  return (
    <StyledMapPage>
      <Map
        mapElement={mapElement}
        onMapInitialized={(map: naver.maps.Map) => setMap(map)}
        onCurrentViewSearchClick={updateMapDataBasedOnBounds}
      />
      <Panel
        mapElement={mapElement}
        searchedVenus={searchedVenus}
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
