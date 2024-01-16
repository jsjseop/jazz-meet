import styled from '@emotion/styled';
import { useEffect, useState } from 'react';
import {
  HOVER_MARKER_Z_INDEX,
  MARKER_Z_INDEX,
  SELECTED_MARKER_Z_INDEX,
} from '~/constants/Z_INDEX';
import { RenderType } from '~/types/device.types';
import { getInitMap } from '~/utils/map';
import { MapSearchButton } from './MapSearchButton';

type Props = {
  mapElement: React.RefObject<HTMLDivElement>;
  renderType: RenderType;
  onMapInitialized: (map: naver.maps.Map) => void;
  onCurrentViewSearchClick: () => void;
};

export const Map: React.FC<Props> = ({
  mapElement,
  renderType,
  onMapInitialized,
  onCurrentViewSearchClick,
}) => {
  const [isShowMapSearchButton, setIsMapShowSearchButton] = useState(false);
  const showMapSearchButton = () => setIsMapShowSearchButton(true);
  const hideMapSearchButton = () => setIsMapShowSearchButton(false);

  useEffect(() => {
    const map = getInitMap();
    onMapInitialized(map);

    const events = ['dragend', 'mousewheel', 'pinch'];
    const eventListeners = events.map((event) =>
      naver.maps.Event.addListener(map, event, showMapSearchButton),
    );

    return () => {
      eventListeners.forEach((eventListener) =>
        naver.maps.Event.removeListener(eventListener),
      );
    };
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  return (
    <StyledMap id="map" ref={mapElement} renderType={renderType}>
      {isShowMapSearchButton && (
        <MapSearchButton
          hideMapSearchButton={hideMapSearchButton}
          onCurrentViewSearchClick={onCurrentViewSearchClick}
        />
      )}
    </StyledMap>
  );
};

const StyledMap = styled.div<{ renderType: RenderType }>`
  ${({ renderType }) =>
    renderType === 'all' || renderType === 'map' ? '' : 'display: none;'};

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
`;
