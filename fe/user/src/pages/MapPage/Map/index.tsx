import styled from '@emotion/styled';
import RefreshIcon from '@mui/icons-material/Refresh';
import { useCallback, useEffect, useRef, useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import { getVenuePinsByMapBounds, getVenuePinsBySearch } from '~/apis/venue';
import { BASIC_COORDINATE } from '~/constants/COORDINATE';
import { useUserCoordinate } from '~/hooks/useUserCoordinate';
import { Coordinate } from '~/types/map.types';
import { addPinsOnMap, fitBoundsToPins } from '~/utils/map';

type Props = {
  mapRef: React.RefObject<HTMLDivElement>;
};

export const Map: React.FC<Props> = ({ mapRef }) => {
  const navigate = useNavigate();
  const { search: searchQueryString } = useLocation();
  const { userCoordinate } = useUserCoordinate();
  const [isBoundsChanged, setIsBoundsChanged] = useState(false);
  const map = useRef<naver.maps.Map>();
  const markers = useRef<naver.maps.Marker[]>();

  const updatePins = useCallback(async () => {
    if (!map.current) return;

    if (searchQueryString.includes('word')) {
      const pins = await getVenuePinsBySearch(searchQueryString);
      fitBoundsToPins(pins, map.current);
      markers.current = addPinsOnMap(pins, map.current, 'pin');

      setIsBoundsChanged(false);
    }

    if (searchQueryString.includes('lowLatitude')) {
      const pins = await getVenuePinsByMapBounds(searchQueryString);
      markers.current = addPinsOnMap(pins, map.current, 'pin');
    }
  }, [searchQueryString]);

  useEffect(() => {
    let boundsChangeEventListener: naver.maps.MapEventListener;
    let dragendEventListener: naver.maps.MapEventListener;
    (async () => {
      const updateView = async () => {
        if (!map.current || !searchQueryString) {
          return;
        }

        if (markers.current) {
          markers.current.forEach((marker) => marker.setMap(null));
        }

        await updatePins();
      };

      await updateView();

      boundsChangeEventListener = naver.maps.Event.addListener(
        map.current,
        'zoom_changed',
        () => setIsBoundsChanged(true),
      );
      dragendEventListener = naver.maps.Event.addListener(
        map.current,
        'dragend',
        () => setIsBoundsChanged(true),
      );
    })();

    return () => {
      if (!map.current) {
        return;
      }

      naver.maps.Event.removeListener(boundsChangeEventListener);
      naver.maps.Event.removeListener(dragendEventListener);
    };
  }, [searchQueryString, updatePins]);

  useEffect(() => {
    if (!map.current) {
      map.current = getInitMap(userCoordinate);
    }
  }, [userCoordinate]);

  const onMapSearchButtonClick = () => {
    if (!map.current) {
      throw new Error('map is not initialized');
    }

    const bounds = map.current.getBounds();

    if (!(bounds instanceof naver.maps.LatLngBounds)) {
      return;
    }

    navigate(
      `/map?lowLatitude=${bounds.south()}&highLatitude=${bounds.north()}&lowLongitude=${bounds.west()}&highLongitude=${bounds.east()}`,
    );

    setIsBoundsChanged(false);
  };

  return (
    <StyledMap id="map" ref={mapRef}>
      {isBoundsChanged && (
        <StyledMapSearchButton onClick={onMapSearchButtonClick}>
          <RefreshIcon />
          <span>현 지도에서 검색</span>
        </StyledMapSearchButton>
      )}
    </StyledMap>
  );
};

const getInitMap = (userCoordinate: Coordinate | null) => {
  const initCoordinate = userCoordinate ?? BASIC_COORDINATE;

  return new naver.maps.Map('map', {
    center: new naver.maps.LatLng(
      initCoordinate.latitude,
      initCoordinate.longitude,
    ),
  });
};

const StyledMap = styled.div`
  width: 100%;
  height: inherit;
`;

const StyledMapSearchButton = styled.button`
  position: absolute;
  bottom: 8%;
  left: 50%;
  transform: translateX(-50%);
  z-index: 1000;
  padding: 12px 16px;
  border-radius: 50px;
  display: flex;
  align-items: center;
  gap: 9px;
  color: #fff;
  background-color: #0775f4;

  &:hover {
    cursor: pointer;
    background-color: #0a84ff;
  }
`;
