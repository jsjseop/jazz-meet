import { useEffect, useRef, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import {
  getVenuePinsByMapBounds,
  getVenuePinsBySearch,
  getVenuesByKeyword,
  getVenuesByMapBounds,
} from '~/apis/venue';
import { Pin, SearchedVenues } from '~/types/api.types';
import {
  addMarkersOnMap,
  addPinsOnMap,
  fitBoundsToPins,
  getMapBounds,
} from '~/utils/map';

export const useMapDataUpdater = (map?: naver.maps.Map) => {
  const [pins, setPins] = useState<Pin[]>();
  const [searchedVenus, setSearchedVenues] = useState<SearchedVenues>();

  const pinsOnMap = useRef<naver.maps.Marker[]>();
  const markersOnMap = useRef<naver.maps.Marker[]>();

  const navigate = useNavigate();

  const updateMapDataBasedOnBounds = () => {
    if (!map) {
      return;
    }

    const bounds = getMapBounds(map);

    if (!bounds) {
      return;
    }

    (async () => {
      const [pins, venueList] = await Promise.all([
        getVenuePinsByMapBounds(bounds),
        getVenuesByMapBounds(bounds),
      ]);

      setPins(pins);
      setSearchedVenues(venueList);
    })();
  };

  const updateMapDataBySearch = async (word: string) => {
    const [pins, venueList] = await Promise.all([
      getVenuePinsBySearch(word),
      getVenuesByKeyword({ word }),
    ]);

    setPins(pins);
    setSearchedVenues(venueList);
  };

  const handleChangeVenueListPage = (page: number) => {
    if (!map) {
      return;
    }

    const bounds = getMapBounds(map);

    if (!bounds) {
      return;
    }

    (async () => {
      const venueList = await getVenuesByMapBounds({ ...bounds, page });
      setSearchedVenues(venueList);
    })();
  };

  // pins, searchedVenues가 변경될 때 렌더링 한다.
  useEffect(() => {
    if (!map || !pins || !searchedVenus) {
      return;
    }

    if (pinsOnMap.current) {
      pinsOnMap.current.forEach((marker) => marker.setMap(null));
    }

    if (markersOnMap.current) {
      markersOnMap.current.forEach((marker) => marker.setMap(null));
    }

    const filteredPins = pins.filter((pin) =>
      searchedVenus.venues.every((venue) => venue.id !== pin.id),
    );

    const goToVenueDetail = (venueId: number) => {
      navigate(`venues/${venueId}`);
    };

    pinsOnMap.current = addPinsOnMap(filteredPins, map, goToVenueDetail);
    markersOnMap.current = addMarkersOnMap(
      searchedVenus.venues,
      map,
      goToVenueDetail,
    );

    fitBoundsToPins(pins, map);
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [map, pins, searchedVenus]);

  return {
    searchedVenus,
    updateMapDataBasedOnBounds,
    updateMapDataBySearch,
    handleChangeVenueListPage,
  };
};
