import styled from '@emotion/styled';
import { useCallback, useEffect, useRef, useState } from 'react';
import { Map } from './Map';
import { Panel } from './Panel';
import { getVenuePinsByMapBounds, getVenuesByMapBounds } from '~/apis/venue';
import { Pin, SearchedVenues } from '~/types/api.types';
import { addMarkersOnMap, addPinsOnMap, getMapBounds } from '~/utils/map';
import { useNavigate } from 'react-router-dom';

export const MapPage: React.FC = () => {
  const mapElement = useRef<HTMLDivElement>(null);
  const [map, setMap] = useState<naver.maps.Map>();

  const [pins, setPins] = useState<Pin[]>();
  const [searchedVenus, setSearchedVenues] = useState<SearchedVenues>();

  const pinsOnMap = useRef<naver.maps.Marker[]>();
  const markersOnMap = useRef<naver.maps.Marker[]>();

  const navigate = useNavigate();

  const updateMapDataBasedOnBounds = useCallback(() => {
    if (!map) {
      return;
    }

    const bounds = getMapBounds(map);

    if (!bounds) {
      return;
    }

    (async () => {
      const [pins, venueList] = await Promise.all([
        getVenuePinsByMapBounds(bounds),
        getVenuesByMapBounds(bounds),
      ]);

      setPins(pins);
      setSearchedVenues(venueList);
    })();
  }, [map]);

  // 지도가 첫 렌더링 될 때
  useEffect(() => {
    updateMapDataBasedOnBounds();
  }, [updateMapDataBasedOnBounds]);

  // pins, searchedVenues가 변경될 때 렌더링 한다.
  useEffect(() => {
    if (!map || !pins || !searchedVenus) {
      return;
    }

    if (pinsOnMap.current) {
      pinsOnMap.current.forEach((marker) => marker.setMap(null));
    }

    if (markersOnMap.current) {
      markersOnMap.current.forEach((marker) => marker.setMap(null));
    }

    const filteredPins = pins.filter((pin) =>
      searchedVenus.venues.every((venue) => venue.id !== pin.id),
    );

    const goToVenueDetail = (venueId: number) => {
      navigate(`venues/${venueId}`);
    };

    pinsOnMap.current = addPinsOnMap(filteredPins, map, goToVenueDetail);
    markersOnMap.current = addMarkersOnMap(
      searchedVenus.venues,
      map,
      goToVenueDetail,
    );
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [map, pins, searchedVenus]);

  return (
    <StyledMapPage>
      <Map
        mapElement={mapElement}
        map={map}
        onMapInitialized={(map: naver.maps.Map) => setMap(map)}
        onCurrentViewSearchClick={updateMapDataBasedOnBounds}
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
