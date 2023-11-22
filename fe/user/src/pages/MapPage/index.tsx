import styled from '@emotion/styled';
import { useEffect, useRef, useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import { useMapDataUpdater } from '~/hooks/useMapDataUpdater';
import { getQueryString } from '~/utils/getQueryString';
import { getMapBounds, getQueryBounds } from '~/utils/map';
import { Map } from './Map';
import { Panel } from './Panel';

export const MapPage: React.FC = () => {
  const [map, setMap] = useState<naver.maps.Map>();
  const {
    searchedVenues,
    handleChangeVenueListPage,
    handleMapDataUpdateWithBounds,
    handleMapDataWithWord,
    handleMapDataWithVenueId,
  } = useMapDataUpdater(map);
  const mapElement = useRef<HTMLDivElement>(null);

  const { search } = useLocation();
  const navigate = useNavigate();
  const word = new URLSearchParams(search).get('word');
  const venueId = new URLSearchParams(search).get('venueId');

  const navigateWithMapBounds = () => {
    if (!map) {
      return;
    }

    const bounds = getMapBounds(map);

    if (!bounds) {
      return;
    }

    navigate(`/map${getQueryString(bounds)}`);
  };

  useEffect(() => {
    if (word) {
      handleMapDataWithWord(word);

      return;
    }

    if (venueId) {
      handleMapDataWithVenueId(venueId);

      return;
    }

    const bounds = getQueryBounds(search);

    handleMapDataUpdateWithBounds(bounds);
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [map, word, venueId, search]);

  return (
    <StyledMapPage>
      <Map
        mapElement={mapElement}
        onMapInitialized={(map: naver.maps.Map) => setMap(map)}
        onCurrentViewSearchClick={navigateWithMapBounds}
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
