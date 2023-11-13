import styled from '@emotion/styled';
import { useEffect, useState } from 'react';
import { getInitMap } from '~/utils/map';
import { MapSearchButton } from './MapSearchButton';

type Props = {
  mapElement: React.RefObject<HTMLDivElement>;
  onMapInitialized: (map: naver.maps.Map) => void;
  onCurrentViewSearchClick: () => void;
};

export const Map: React.FC<Props> = ({
  mapElement,
  onMapInitialized,
  onCurrentViewSearchClick,
}) => {
  const [isShowMapSearchButton, setIsMapShowSearchButton] = useState(false);
  const showMapSearchButton = () => setIsMapShowSearchButton(true);
  const hideMapSearchButton = () => setIsMapShowSearchButton(false);

  useEffect(() => {
    const map = getInitMap(null);
    onMapInitialized(map);

    const boundsChangeEventListener = naver.maps.Event.addListener(
      map,
      'zoom_changed',
      showMapSearchButton,
    );
    const dragendEventListener = naver.maps.Event.addListener(
      map,
      'dragend',
      showMapSearchButton,
    );

    return () => {
      naver.maps.Event.removeListener(boundsChangeEventListener);
      naver.maps.Event.removeListener(dragendEventListener);
    };
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  return (
    <StyledMap id="map" ref={mapElement}>
      {isShowMapSearchButton && (
        <MapSearchButton
          hideMapSearchButton={hideMapSearchButton}
          onCurrentViewSearchClick={onCurrentViewSearchClick}
        />
      )}
    </StyledMap>
  );
};

const StyledMap = styled.div`
  width: 100%;
  height: inherit;

  .marker-container {
    background-color: #47484e;
    white-space: nowrap;
    border-radius: 24px 24px 24px 3px;
    padding: 5px 10px 5px 5px;
    box-shadow: 5px 5px 10px rgba(0, 0, 0, 0.25);
    display: flex;
    align-items: center;
    gap: 5px;
  }

  .marker-icon-container {
    width: 38px;
    height: 38px;
    background-color: #ffffff;
    border-radius: 50%;
    display: flex;
    align-items: center;
    justify-content: center;
  }

  .marker-text {
    color: #ffffff;
  }
`;
