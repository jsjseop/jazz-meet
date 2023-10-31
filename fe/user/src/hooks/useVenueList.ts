import { getVenuesByKeyword } from 'apis/venue';
import { useCallback, useEffect, useMemo, useState } from 'react';
import { useLocation } from 'react-router-dom';
import { SearchParams, SearchedVenues } from 'types/api.types';

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
      const searchParams = getSearchParams(page, urlSearchParams);
      const searchedVenues = await getVenuesByKeyword(searchParams);

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

const getSearchParams = (
  page: number | undefined,
  urlSearchParams: URLSearchParams,
) => {
  const searchParams: SearchParams = {};

  if (page && page > 1) {
    searchParams.page = page;
  }

  if (urlSearchParams.get('word')) {
    searchParams.word = urlSearchParams.get('word') ?? '';
  }

  return searchParams;
};
