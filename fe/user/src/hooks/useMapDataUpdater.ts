import { useEffect, useRef, useState } from 'react';
import { useLocation, useNavigate, useParams } from 'react-router-dom';
import {
  getSingleVenue,
  getVenuePinsByMapBounds,
  getVenuePinsBySearch,
  getVenuesByKeyword,
  getVenuesByMapBounds,
} from '~/apis/venue';
import { Pin, SearchedVenues } from '~/types/api.types';
import { CoordinateBoundary } from '~/types/map.types';
import { getQueryString } from '~/utils/getQueryString';
import {
  addMarkersOnMap,
  addPinsOnMap,
  fitBoundsToBoundary,
  fitBoundsToCoordinates,
  getMapBounds,
} from '~/utils/map';

export const useMapDataUpdater = (map?: naver.maps.Map) => {
  const navigate = useNavigate();
  const { venueId } = useParams();
  const { search } = useLocation();

  const [pins, setPins] = useState<Pin[]>();
  const [searchedVenues, setSearchedVenues] = useState<SearchedVenues>();

  const pinsOnMap = useRef<naver.maps.Marker[]>();
  const markersOnMap = useRef<naver.maps.Marker[]>();

  const enableFitBounds = useRef(false);
  const boundary = useRef<CoordinateBoundary>();

  const selectedVenueId = venueId ? Number(venueId) : -1;

  const searchBasedOnBounds = () => {
    if (!map) {
      return;
    }

    const bounds = getMapBounds(map);

    if (!bounds) {
      return;
    }

    navigate(`/map${getQueryString(bounds)}`);
  };

  const updateMapDataBasedOnBounds = async (
    queryBounds?: CoordinateBoundary,
  ) => {
    if (!map) {
      return;
    }

    const bounds = queryBounds === undefined ? getMapBounds(map) : queryBounds;

    if (!bounds) {
      return;
    }

    const [pins, venueList] = await Promise.all([
      getVenuePinsByMapBounds(bounds),
      getVenuesByMapBounds(bounds),
    ]);

    setPins(pins);
    setSearchedVenues(venueList);
    boundary.current = bounds;
    enableFitBounds.current = false;
  };

  const updateMapDataBySearch = async (word: string) => {
    const [pins, venueList] = await Promise.all([
      getVenuePinsBySearch(word),
      getVenuesByKeyword({ word }),
    ]);

    setPins(pins);
    setSearchedVenues(venueList);
    enableFitBounds.current = true;
  };

  const updateMapDataByVenueId = async (venueId: string) => {
    const venueList = await getSingleVenue(venueId);

    setPins([]);
    setSearchedVenues(venueList);
    enableFitBounds.current = true;
  };

  const handleChangeVenueListPage = async (page: number) => {
    if (!map) {
      return;
    }

    const bounds = getMapBounds(map);

    if (!bounds) {
      return;
    }

    const venueList = await getVenuesByMapBounds({ ...bounds, page });

    setSearchedVenues(venueList);
  };

  // pins, searchedVenues가 변경될 때 렌더링 한다.
  useEffect(() => {
    if (!map || !pins || !searchedVenues) {
      return;
    }

    if (pinsOnMap.current) {
      pinsOnMap.current.forEach((marker) => marker.setMap(null));
    }

    if (markersOnMap.current) {
      markersOnMap.current.forEach((marker) => marker.setMap(null));
    }

    const filteredPins = pins.filter((pin) =>
      searchedVenues.venues.every((venue) => venue.id !== pin.id),
    );

    const goToVenueDetail = (venueId: number) => {
      navigate(`venues/${venueId}${search}`);
    };

    pinsOnMap.current = addPinsOnMap({
      pins: filteredPins,
      map,
      selectedVenueId,
      onPinClick: (venueId) => goToVenueDetail(venueId),
    });
    markersOnMap.current = addMarkersOnMap({
      pins: searchedVenues.venues,
      map,
      selectedVenueId,
      onMarkerClick: (venueId) => goToVenueDetail(venueId),
    });

    if (enableFitBounds.current) {
      fitBoundsToCoordinates([...filteredPins, ...searchedVenues.venues], map);
      enableFitBounds.current = false;
      return;
    }

    if (boundary.current) {
      console.log('boundary.current', boundary.current);
      fitBoundsToBoundary(boundary.current, map);

      boundary.current = undefined;
    }

    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [map, pins, searchedVenues, selectedVenueId]);

  useEffect(() => {
    if (!map || !pins) {
      return;
    }

    if (selectedVenueId !== -1) {
      const selectedVenue = pins.find((pin) => pin.id === selectedVenueId);

      if (selectedVenue) {
        map.panTo(
          new naver.maps.LatLng(
            selectedVenue.latitude,
            selectedVenue.longitude,
          ),
        );
      }
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [selectedVenueId]);

  return {
    searchedVenues,
    searchBasedOnBounds,
    updateMapDataBasedOnBounds,
    updateMapDataBySearch,
    updateMapDataByVenueId,
    handleChangeVenueListPage,
  };
};
