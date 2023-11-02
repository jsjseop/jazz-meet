import styled from '@emotion/styled';
import RefreshIcon from '@mui/icons-material/Refresh';
import { useEffect, useRef, useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import { getVenuePinsBySearch } from '~/apis/venue';
import { BASIC_COORDINATE } from '~/constants/COORDINATE';
import { useUserCoordinate } from '~/hooks/useUserCoordinate';
import { Coordinate } from '~/types/map.types';
import { debounce } from '~/utils/debounce';
import { addPinsOnMap, fitBoundsToPins } from '~/utils/map';

type Props = {
  mapRef: React.RefObject<HTMLDivElement>;
};

export const Map: React.FC<Props> = ({ mapRef }) => {
  const navigate = useNavigate();
  const { search: searchQueryString } = useLocation();
  const { userCoordinate } = useUserCoordinate();
  const [isBoundsChanged, setIsBoundsChanged] = useState(false);
  const map = useRef<naver.maps.Map | null>(null);

  useEffect(() => {
    const updateView = async () => {
      map.current = getInitMap(userCoordinate);

      naver.maps.Event.addListener(
        map.current,
        'bounds_changed',
        debounce(() => {
          setIsBoundsChanged(true);
        }, 100),
      );

      if (!searchQueryString) {
        return;
      }

      const pins = await getVenuePinsBySearch(searchQueryString);

      fitBoundsToPins(pins, map.current);
      addPinsOnMap(pins, map.current, 'pin');
    };

    updateView();
  }, [searchQueryString, userCoordinate]);

  return (
    <StyledMap id="map" ref={mapRef}>
      {isBoundsChanged && (
        <StyledButton
          onClick={() => {
            console.log('bounds', map.current?.getBounds());
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
          }}
        >
          <RefreshIcon />
          <span>현 지도에서 검색</span>
        </StyledButton>
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

const StyledButton = styled.button`
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
    background-color: #0a84ff;
  }
`;
