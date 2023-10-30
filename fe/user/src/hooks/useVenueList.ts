import { getVenuesByKeyword } from 'apis/venue';
import { useCallback, useEffect, useState } from 'react';
import { VenueData } from 'types/api.types';

export const useVenueList = () => {
  const [venueList, setVenueList] = useState<VenueData[]>([]);
  const [venueCount, setVenueCount] = useState(0);
  const [currentPage, setCurrentPage] = useState(1);
  const [maxPage, setMaxPage] = useState(1);

  const updateVenueList = useCallback(async (page: number = 1) => {
    const { venues, venueCount, currentPage, maxPage } = await getVenuesByKeyword({
      page
    });

    setVenueList(venues);
    setVenueCount(venueCount);
    setCurrentPage(currentPage);
    setMaxPage(maxPage);
  }, []);

  useEffect(() => {
    updateVenueList();
  }, []);

  return { venueList, venueCount, currentPage, maxPage, updateVenueList };
};
