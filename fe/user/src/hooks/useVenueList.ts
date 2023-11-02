import { useCallback, useEffect, useMemo, useState } from 'react';
import { useLocation } from 'react-router-dom';
import { getVenuesByKeyword } from '~/apis/venue';
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

      if (!word) {
        return;
      }

      const searchedVenues = await getVenuesByKeyword({ page, word });

      setVenueListData(searchedVenues);
    },
    [urlSearchParams],
  );

  useEffect(() => {
    updateVenueList();
  }, []);

  return {
    venueList: venueListData.venues,
    venueCount: venueListData.venueCount,
    currentPage: venueListData.currentPage,
    maxPage: venueListData.maxPage,
    updateVenueList,
  };
};
