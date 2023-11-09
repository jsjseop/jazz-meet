import { useCallback, useEffect, useRef } from 'react';
import { useNavigate } from 'react-router-dom';
import { getVenuePinsByMapBounds, getVenuePinsBySearch } from '~/apis/venue';
import { Pin, VenueData } from '~/types/api.types';
import {
  addMarkersOnMap,
  addPinsOnMap,
  fitBoundsToCoordinateBoundary,
  fitBoundsToPins,
} from '~/utils/map';

export const useMarkers = ({
  map,
  searchQueryString,
  hideMapSearchButton,
  venueList,
}: {
  map?: naver.maps.Map;
  searchQueryString: string;
  hideMapSearchButton: () => void;
  venueList: VenueData[];
}) => {
  const pins = useRef<Pin[]>([]);

  const pinsOnMap = useRef<naver.maps.Marker[]>([]);
  const markersOnMap = useRef<naver.maps.Marker[]>([]);

  const navigate = useNavigate();
  const goToVenueDetail = (venueId: number) => {
    navigate(`venues/${venueId}`);
  };

  useEffect(() => {
    const getPins = async () => {
      if (!map) return;

      if (searchQueryString.includes('word')) {
        pins.current = await getVenuePinsBySearch(searchQueryString);
      } else if (
        searchQueryString.includes('lowLatitude') &&
        searchQueryString.includes('lowLongitude') &&
        searchQueryString.includes('highLatitude') &&
        searchQueryString.includes('highLongitude')
      ) {
        pins.current = await getVenuePinsByMapBounds(searchQueryString);
      } else if (!searchQueryString.includes('venueId')) {
        const bounds = map.getBounds();

        if (!(bounds instanceof naver.maps.LatLngBounds)) {
          return;
        }

        const boundsQueryString = `?lowLatitude=${bounds.south()}&highLatitude=${bounds.north()}&lowLongitude=${bounds.west()}&highLongitude=${bounds.east()}`;

        pins.current = await getVenuePinsByMapBounds(boundsQueryString);
      }
    };

    getPins();
  }, [map, searchQueryString]);

  const updatePins = useCallback(async () => {
    if (!map) return;

    if (pinsOnMap.current) {
      pinsOnMap.current.forEach((marker) => marker.setMap(null));
    }

    if (markersOnMap.current) {
      markersOnMap.current.forEach((marker) => marker.setMap(null));
    }

    const filteredPins = pins.current.filter((pin) =>
      venueList.every((venue) => venue.id !== pin.id),
    );

    if (searchQueryString.includes('word')) {
      pinsOnMap.current = addPinsOnMap(
        filteredPins,
        map,
        goToVenueDetail,
      );
      markersOnMap.current = addMarkersOnMap(
        venueList,
        map,
        goToVenueDetail,
      );

      fitBoundsToPins(pins.current, map);
    } else if (
      searchQueryString.includes('lowLatitude') &&
      searchQueryString.includes('lowLongitude') &&
      searchQueryString.includes('highLatitude') &&
      searchQueryString.includes('highLongitude')
    ) {
      pinsOnMap.current = addPinsOnMap(
        filteredPins,
        map,
        goToVenueDetail,
      );
      markersOnMap.current = addMarkersOnMap(
        venueList,
        map,
        goToVenueDetail,
      );

      fitBoundsToCoordinateBoundary(searchQueryString, map);
    } else if (searchQueryString.includes('venueId')) {
      markersOnMap.current = addPinsOnMap(
        venueList,
        map,
        goToVenueDetail,
      );

      fitBoundsToPins(venueList, map);
    } else {
      pinsOnMap.current = addPinsOnMap(
        filteredPins,
        map,
        goToVenueDetail,
      );
      markersOnMap.current = addMarkersOnMap(
        venueList,
        map,
        goToVenueDetail,
      );
    }

    hideMapSearchButton();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [map, searchQueryString, venueList, pinsOnMap, markersOnMap]);

  return { pinsOnMap, updatePins };
};
