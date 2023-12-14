import styled from '@emotion/styled';
import { useEffect, useState } from 'react';
import MyLocation from '~/assets/icons/MyLocation.svg';
import {
  HOVER_MARKER_Z_INDEX,
  MARKER_Z_INDEX,
  SELECTED_MARKER_Z_INDEX,
} from '~/constants/Z_INDEX';
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

    const dragendEventListener = naver.maps.Event.addListener(
      map,
      'dragend',
      showMapSearchButton,
    );

    const currentLocationButton = `<div class='my-location-button'><img src=${MyLocation} alt='현재 위치로 이동' /></div>`;
    const customControl = new naver.maps.CustomControl(currentLocationButton, {
      position: naver.maps.Position.RIGHT_BOTTOM,
    });

    naver.maps.Event.once(map, 'init', () => {
      customControl.setMap(map);
    });

    return () => {
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

  .marker {
    position: relative;
    background-color: #ffffff;
    white-space: nowrap;
    border: 1px solid #efeff0;
    border-radius: 24px 24px 24px 3px;
    padding: 5px 10px 5px 5px;
    box-shadow: 5px 5px 10px rgba(0, 0, 0, 0.25);
    display: flex;
    align-items: center;
    gap: 5px;
    z-index: ${MARKER_Z_INDEX};

    &:hover {
      z-index: ${HOVER_MARKER_Z_INDEX};
      box-shadow: 5px 5px 10px rgba(0, 0, 0, 0.5);
    }

    &--icon {
      width: 38px;
      height: 38px;
      background-color: #47484e;
      border-radius: 50%;
      display: flex;
      align-items: center;
      justify-content: center;
    }

    &--text {
      color: #47484e;
    }

    &.active {
      background-color: #47484e;
      border: none;
      z-index: ${SELECTED_MARKER_Z_INDEX};

      .marker--icon {
        background-color: #ffffff;
      }

      .marker--text {
        color: #ffffff;
      }
    }
  }

  .my-location-button {
    width: 35px;
    height: 35px;
    user-select: none;
    background-color: #ffffff;
    border-radius: 5px;
    border: 1px solid #dbdbdb;
    padding: 5px;
    box-sizing: border-box;
    margin: 30px 10px;
    cursor: pointer;
    background-clip: padding-box;

    &:active {
      background-color: #dbdbdb;
    }

    & img {
      width: 100%;
      height: 100%;
    }
  }
`;
