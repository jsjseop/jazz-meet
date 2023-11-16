import { useEffect, useRef, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import {
  getSingleVenue,
  getVenuePinsByMapBounds,
  getVenuePinsBySearch,
  getVenuesByKeyword,
  getVenuesByMapBounds,
} from '~/apis/venue';
import { Pin, SearchedVenues } from '~/types/api.types';
import {
  addMarkersOnMap,
  addPinsOnMap,
  fitBoundsToCoordinates,
  getMapBounds,
} from '~/utils/map';

export const useMapDataUpdater = (map?: naver.maps.Map) => {
  const [pins, setPins] = useState<Pin[]>();
  const [searchedVenues, setSearchedVenues] = useState<SearchedVenues>();
  const [selectedVenueId, setSelectedVenueId] = useState<number>(-1);

  const pinsOnMap = useRef<naver.maps.Marker[]>();
  const markersOnMap = useRef<naver.maps.Marker[]>();

  const enableFitBounds = useRef(false);

  const navigate = useNavigate();

  const updateMapDataBasedOnBounds = async () => {
    if (!map) {
      return;
    }

    const bounds = getMapBounds(map);

    if (!bounds) {
      return;
    }

    const [pins, venueList] = await Promise.all([
      getVenuePinsByMapBounds(bounds),
      getVenuesByMapBounds(bounds),
    ]);

    setPins(pins);
    setSearchedVenues(venueList);
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
      navigate(`venues/${venueId}`);
    };

    pinsOnMap.current = addPinsOnMap({
      pins: filteredPins,
      map,
      selectedVenueId,
      onPinClick: (venueId) => {
        goToVenueDetail(venueId);
        setSelectedVenueId(venueId);
      },
    });
    markersOnMap.current = addMarkersOnMap({
      pins: searchedVenues.venues,
      map,
      selectedVenueId,
      onMarkerClick: (venueId) => {
        goToVenueDetail(venueId);
        setSelectedVenueId(venueId);
      },
    });

    if (enableFitBounds.current) {
      fitBoundsToCoordinates([...filteredPins, ...searchedVenues.venues], map);
      enableFitBounds.current = false;
    }

    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [map, pins, searchedVenues, selectedVenueId]);

  return {
    searchedVenues,
    updateMapDataBasedOnBounds,
    updateMapDataBySearch,
    updateMapDataByVenueId,
    handleChangeVenueListPage,
  };
};
