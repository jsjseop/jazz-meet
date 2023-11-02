import styled from '@emotion/styled';
import { useCallback, useEffect } from 'react';
import { useLocation } from 'react-router-dom';
import { getVenuePinsBySearch } from '~/apis/venue';
import { BASIC_COORDINATE } from '~/constants/COORDINATE';
import { useUserCoordinate } from '~/hooks/useUserCoordinate';
import { addPinsOnMap, fitBoundsToPins } from '~/utils/map';

type Props = {
  mapRef: React.RefObject<HTMLDivElement>;
};

export const Map: React.FC<Props> = ({ mapRef }) => {
  const { search: searchQueryString } = useLocation();
  const { userCoordinate } = useUserCoordinate();

  const getInitMap = useCallback(() => {
    const initCoordinate = userCoordinate ?? BASIC_COORDINATE;

    return new naver.maps.Map('map', {
      center: new naver.maps.LatLng(
        initCoordinate.latitude,
        initCoordinate.longitude,
      ),
    });
  }, [userCoordinate]);

  useEffect(() => {
    const updateView = async () => {
      const map = getInitMap();

      if (!searchQueryString) {
        return;
      }

      const pins = await getVenuePinsBySearch(searchQueryString);

      fitBoundsToPins(pins, map);
      addPinsOnMap(pins, map, 'pin');
    };

    updateView();
  }, [searchQueryString, userCoordinate, getInitMap]);

  return <StyledMap id="map" ref={mapRef} />;
};

const StyledMap = styled.div`
  width: 100%;
  height: inherit;
`;
