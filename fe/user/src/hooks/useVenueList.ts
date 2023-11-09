import { useCallback, useEffect, useMemo, useState } from 'react';
import { useLocation } from 'react-router-dom';
import {
  getSingleVenue,
  getVenuesByKeyword,
  getVenuesByMapBounds,
} from '~/apis/venue';
import { SearchedVenues, VenueData } from '~/types/api.types';
import { CoordinateBoundary } from '~/types/map.types';

export type VenueListData = {
  venueList: VenueData[];
  totalCount: number;
  currentPage: number;
  maxPage: number;
  updateVenueList: (page: number) => void;
};

export const useVenueList = (mapObj: naver.maps.Map | undefined) => {
  const { search } = useLocation();
  const urlSearchParams = useMemo(() => new URLSearchParams(search), [search]);
  const [venueListData, setVenueListData] = useState<SearchedVenues>({
    venues: [],
    totalCount: 0,
    currentPage: 1,
    maxPage: 1,
  });

  const updateVenueList = useCallback(
    async (initialMapBounds: CoordinateBoundary, page?: number) => {
      if ([...urlSearchParams.keys()].length === 0) {
        const searchedVenues = await getVenuesByMapBounds({
          page,
          ...initialMapBounds,
        });

        setVenueListData(searchedVenues);
        return;
      }

      const word = urlSearchParams.get('word');
      const coordinateBoundary = {
        lowLatitude: parseInt(urlSearchParams.get('lowLatitude')!),
        highLatitude: parseInt(urlSearchParams.get('highLatitude')!),
        lowLongitude: parseInt(urlSearchParams.get('lowLongitude')!),
        highLongitude: parseInt(urlSearchParams.get('highLongitude')!),
      };
      const venueId = urlSearchParams.get('venueId');

      const searchedVenues = word
        ? await getVenuesByKeyword({ page, word })
        : venueId
        ? await getSingleVenue(Number(venueId))
        : await getVenuesByMapBounds({ page, ...coordinateBoundary });

      setVenueListData(searchedVenues);
    },
    [urlSearchParams],
  );

  useEffect(() => {
    if (!mapObj) return;

    const bounds = mapObj.getBounds();

    if (!(bounds instanceof naver.maps.LatLngBounds)) {
      return;
    }

    const initialMapBounds = {
      lowLatitude: bounds.south(),
      highLatitude: bounds.north(),
      lowLongitude: bounds.west(),
      highLongitude: bounds.east(),
    };

    updateVenueList(initialMapBounds);
  }, [updateVenueList, mapObj]);

  return {
    venueList: venueListData.venues,
    totalCount: venueListData.totalCount,
    currentPage: venueListData.currentPage,
    maxPage: venueListData.maxPage,
    updateVenueList,
  };
};
