import { getVenuesByKeyword } from 'apis/venue';
import { VenueData } from 'apis/venue/types';
import { useEffect, useState } from 'react';

export const useVenueList = () => {
  const [venueList, setVenueList] = useState<VenueData[]>([]);
  const [venueCount, setVenueCount] = useState(0);
  const [currentPage, setCurrentPage] = useState(1);
  const [maxPage, setMaxPage] = useState(1);

  useEffect(() => {
    const fetchVenueList = async () => {
      const { venues, venueCount, currentPage, maxPage } =
        await getVenuesByKeyword();

      setVenueList(venues);
      setVenueCount(venueCount);
      setCurrentPage(currentPage);
      setMaxPage(maxPage);
    };

    fetchVenueList();
  }, []);

  return { venueList, venueCount, currentPage, maxPage };
};
