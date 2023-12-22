import { useEffect, useRef, useState } from 'react';
import { useLocation, useNavigate, useParams } from 'react-router-dom';
import {
  getSingleVenue,
  getVenuePinsByMapBounds,
  getVenuePinsByWord,
  getVenuesByMapBounds,
  getVenuesByWord,
} from '~/apis/venue';
import { SearchedVenues } from '~/types/api.types';
import { CoordinateBoundary, Pin } from '~/types/map.types';
import {
  addMarkersOnMap,
  addPinsOnMap,
  fitBoundsToBoundary,
  getMapBounds,
  panToCoordinates,
} from '~/utils/map';

export const useMapDataUpdater = ({
  map,
  renderTypeToList,
}: {
  map?: naver.maps.Map;
  renderTypeToList: () => void;
}) => {
  const navigate = useNavigate();
  const { venueId } = useParams();
  const { search } = useLocation();

  const [pins, setPins] = useState<Pin[]>();
  const [searchedVenues, setSearchedVenues] = useState<SearchedVenues>();

  const pinsOnMap = useRef<naver.maps.Marker[]>();
  const markersOnMap = useRef<naver.maps.Marker[]>();

  const selectedVenueId = venueId ? Number(venueId) : -1;

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

  const handleUpdateMapDataWithBounds = async (
    queryBounds?: CoordinateBoundary,
  ) => {
    if (!map) {
      return;
    }

    const bounds = queryBounds ?? getMapBounds(map);

    if (!bounds) {
      return;
    }

    const { pins, searchedVenues } = await getMapDataBasedOnBounds(bounds);

    setPins(pins);
    setSearchedVenues(searchedVenues);

    fitBoundsToBoundary(bounds, map);
  };

  const handleUpdateMapDataWithWord = async (word: string) => {
    if (!map) {
      return;
    }

    const { pins, searchedVenues } = await getMapDataByWord(word);

    setPins(pins);
    setSearchedVenues(searchedVenues);

    panToCoordinates(pins, map);
  };

  const handleUpdateMapDataWithVenueId = async (venueId: string) => {
    if (!map) {
      return;
    }

    const { searchedVenues } = await getMapDataByVenueId(venueId);

    setPins([]);
    setSearchedVenues(searchedVenues);

    panToCoordinates(searchedVenues.venues, map);
  };

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
      onPinClick: (venueId) => {
        goToVenueDetail(venueId);
        renderTypeToList();
      },
    });
    markersOnMap.current = addMarkersOnMap({
      pins: searchedVenues.venues,
      map,
      selectedVenueId,
      onMarkerClick: (venueId) => {
        goToVenueDetail(venueId);
        renderTypeToList();
      },
    });

    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [map, pins, searchedVenues, selectedVenueId]);

  useEffect(() => {
    if (!map || !pins || selectedVenueId === -1) {
      return;
    }

    const selectedVenue = pins.find((pin) => pin.id === selectedVenueId);

    if (selectedVenue) {
      map.panTo(
        new naver.maps.LatLng(selectedVenue.latitude, selectedVenue.longitude),
      );
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [selectedVenueId]);

  return {
    searchedVenues,
    handleChangeVenueListPage,
    handleUpdateMapDataWithBounds,
    handleUpdateMapDataWithWord,
    handleUpdateMapDataWithVenueId,
  };
};

const getMapDataBasedOnBounds = async (bounds: CoordinateBoundary) => {
  const [pins, venueList] = await Promise.all([
    getVenuePinsByMapBounds(bounds),
    getVenuesByMapBounds(bounds),
  ]);

  return {
    pins,
    searchedVenues: venueList,
  };
};

const getMapDataByWord = async (word: string) => {
  const [pins, venueList] = await Promise.all([
    getVenuePinsByWord(word),
    getVenuesByWord({ word }),
  ]);

  return {
    pins,
    searchedVenues: venueList,
  };
};

const getMapDataByVenueId = async (venueId: string) => {
  const venueList = await getSingleVenue(venueId);

  return {
    searchedVenues: venueList,
  };
};
