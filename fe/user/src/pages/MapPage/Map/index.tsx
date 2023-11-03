import styled from '@emotion/styled';
import { useEffect, useRef, useState } from 'react';
import { useLocation } from 'react-router-dom';
import { useMarkers } from '~/hooks/useMarkers';
import { useUserCoordinate } from '~/hooks/useUserCoordinate';
import { getInitMap } from '~/utils/map';
import { MapSearchButton } from './MapSearchButton';

type Props = {
  mapRef: React.RefObject<HTMLDivElement>;
};

export const Map: React.FC<Props> = ({ mapRef }) => {
  const { search: searchQueryString } = useLocation();
  const { userCoordinate } = useUserCoordinate();
  const [isShowMapSearchButton, setIsMapShowSearchButton] = useState(false);
  const showMapSearchButton = () => setIsMapShowSearchButton(true);
  const hideMapSearchButton = () => setIsMapShowSearchButton(false);
  const map = useRef<naver.maps.Map>();
  const { updatePins } = useMarkers({
    map,
    searchQueryString,
    hideMapSearchButton,
  });

  useEffect(() => {
    map.current = getInitMap(userCoordinate);

    const boundsChangeEventListener = naver.maps.Event.addListener(
      map.current,
      'zoom_changed',
      showMapSearchButton,
    );
    const dragendEventListener = naver.maps.Event.addListener(
      map.current,
      'dragend',
      showMapSearchButton,
    );

    return () => {
      naver.maps.Event.removeListener(boundsChangeEventListener);
      naver.maps.Event.removeListener(dragendEventListener);
    };
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  useEffect(() => {
    updatePins();
  }, [updatePins]);

  return (
    <StyledMap id="map" ref={mapRef}>
      {isShowMapSearchButton && (
        <MapSearchButton map={map} hideMapSearchButton={hideMapSearchButton} />
      )}
    </StyledMap>
  );
};

const StyledMap = styled.div`
  width: 100%;
  height: inherit;
`;
