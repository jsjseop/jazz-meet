import { useCallback, useEffect, useMemo, useState } from 'react';
import { useLocation } from 'react-router-dom';
import { getVenuesByKeyword, getVenuesByMapBounds } from '~/apis/venue';
import { SearchedVenues } from '~/types/api.types';

export const useVenueList = () => {
  const { search } = useLocation();
  const urlSearchParams = useMemo(() => new URLSearchParams(search), [search]);
  const [venueListData, setVenueListData] = useState<SearchedVenues>({
    venues: [],
    venueCount: 0,
    currentPage: 1,
    maxPage: 1,
  });

  const updateVenueList = useCallback(
    async (page?: number) => {
      const word = urlSearchParams.get('word');
      const coordinateBoundary = {
        lowLatitude: Number(urlSearchParams.get('lowLatitude')!),
        highLatitude: Number(urlSearchParams.get('highLatitude')!),
        lowLongitude: Number(urlSearchParams.get('lowLongitude')!),
        highLongitude: Number(urlSearchParams.get('highLongitude')!),
      };

      const searchedVenues = word
        ? await getVenuesByKeyword({ page, word })
        : await getVenuesByMapBounds({ page, ...coordinateBoundary });

      setVenueListData(searchedVenues);
    },
    [urlSearchParams],
  );

  useEffect(() => {
    updateVenueList();
  }, [updateVenueList]);

  return {
    venueList: venueListData.venues,
    venueCount: venueListData.venueCount,
    currentPage: venueListData.currentPage,
    maxPage: venueListData.maxPage,
    updateVenueList,
  };
};
