import styled from '@emotion/styled';
import RefreshIcon from '@mui/icons-material/Refresh';
import { useCallback, useEffect, useState } from 'react';
import { useLocation } from 'react-router-dom';
import { getVenuePinsBySearch } from '~/apis/venue';
import { BASIC_COORDINATE } from '~/constants/COORDINATE';
import { useUserCoordinate } from '~/hooks/useUserCoordinate';
import { debounce } from '~/utils/debounce';
import { addPinsOnMap, fitBoundsToPins } from '~/utils/map';

type Props = {
  mapRef: React.RefObject<HTMLDivElement>;
};

export const Map: React.FC<Props> = ({ mapRef }) => {
  const { search: searchQueryString } = useLocation();
  const { userCoordinate } = useUserCoordinate();
  const [isBoundsChanged, setIsBoundsChanged] = useState(false);

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

      naver.maps.Event.addListener(
        map,
        'bounds_changed',
        debounce(() => {
          setIsBoundsChanged(true);
        }, 100),
      );

      if (!searchQueryString) {
        return;
      }

      const pins = await getVenuePinsBySearch(searchQueryString);

      fitBoundsToPins(pins, map);
      addPinsOnMap(pins, map, 'pin');
    };

    updateView();
  }, [searchQueryString, userCoordinate, getInitMap]);

  return (
    <StyledMap id="map" ref={mapRef}>
      {isBoundsChanged && (
        <StyledButton>
          <RefreshIcon />
          <span>현 지도에서 검색</span>
        </StyledButton>
      )}
    </StyledMap>
  );
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
