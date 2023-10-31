import { getVenuesByKeyword } from 'apis/venue';
import { useCallback, useEffect, useState } from 'react';
import { SearchedVenues } from 'types/api.types';

export const useVenueList = () => {
  const [venueListData, setVenueListData] = useState<SearchedVenues>({
    venues: [],
    venueCount: 0,
    currentPage: 1,
    maxPage: 1,
  });

  const updateVenueList = useCallback(async (page: number = 1) => {
    const searchedVenues = await getVenuesByKeyword({ page });

    setVenueListData(searchedVenues);
  }, []);

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
