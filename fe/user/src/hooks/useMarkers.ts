import { useCallback, useRef } from 'react';
import { getVenuePinsByMapBounds, getVenuePinsBySearch } from '~/apis/venue';
import {
  addPinsOnMap,
  fitBoundsToCoordinateBoundary,
  fitBoundsToPins,
} from '~/utils/map';

export const useMarkers = ({
  map,
  searchQueryString,
  hideMapSearchButton,
}: {
  map: React.MutableRefObject<naver.maps.Map | undefined>;
  searchQueryString: string;
  hideMapSearchButton: () => void;
}) => {
  const markers = useRef<naver.maps.Marker[]>();

  const updatePins = useCallback(async () => {
    if (!map.current) return;

    if (markers.current) {
      markers.current.forEach((marker) => marker.setMap(null));
    }

    if (searchQueryString.includes('word')) {
      const pins = await getVenuePinsBySearch(searchQueryString);
      markers.current = addPinsOnMap(pins, map.current, 'pin');

      fitBoundsToPins(pins, map.current);
    } else if (
      searchQueryString.includes('lowLatitude') &&
      searchQueryString.includes('lowLongitude') &&
      searchQueryString.includes('highLatitude') &&
      searchQueryString.includes('highLongitude')
    ) {
      const pins = await getVenuePinsByMapBounds(searchQueryString);
      markers.current = addPinsOnMap(pins, map.current, 'pin');

      fitBoundsToCoordinateBoundary(searchQueryString, map.current);
    } else {
      const bounds = map.current.getBounds();

      if (!(bounds instanceof naver.maps.LatLngBounds)) {
        return;
      }

      const boundsQueryString = `?lowLatitude=${bounds.south()}&highLatitude=${bounds.north()}&lowLongitude=${bounds.west()}&highLongitude=${bounds.east()}`;

      const pins = await getVenuePinsByMapBounds(boundsQueryString);
      markers.current = addPinsOnMap(pins, map.current, 'pin');
    }

    hideMapSearchButton();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [map, markers, searchQueryString]);

  return { markers, updatePins };
};
