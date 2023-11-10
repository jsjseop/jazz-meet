import styled from '@emotion/styled';
import { useRef, useState } from 'react';
import { Map } from './Map';
import { Panel } from './Panel';
import { useMapDataUpdater } from '~/hooks/useMapDataUpdater';

export const MapPage: React.FC = () => {
  const mapElement = useRef<HTMLDivElement>(null);
  const [map, setMap] = useState<naver.maps.Map>();
  const {
    searchedVenus,
    updateMapDataBasedOnBounds,
    handleChangeVenueListPage,
  } = useMapDataUpdater(map);

  return (
    <StyledMapPage>
      <Map
        mapElement={mapElement}
        map={map}
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
