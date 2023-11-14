import { HasShowDates, ShowDetail, UpcomingShow } from '~/types/api.types';
import { getFormattedDate, getFormattedYearMonth } from '~/utils/dateUtils';
import { fetchData } from './fetchData';

export const getUpcomingShows = async (): Promise<UpcomingShow[]> => {
  const response = await fetchData(`/api/shows/upcoming`);

  return response.json();
};

export const getVenueShowDates = async ({
  venueId,
  date,
}: {
  venueId: string;
  date: Date;
}): Promise<HasShowDates> => {
  const formattedDate = getFormattedYearMonth(date);
  const response = await fetchData(
    `/api/venues/${venueId}/shows/calendar?date=${formattedDate}`,
  );

  return response.json();
};

export const getVenueShowsByDate = async ({
  venueId,
  date,
}: {
  venueId: string;
  date: Date;
}): Promise<ShowDetail[]> => {
  const formattedDate = getFormattedDate(date);

  const response = await fetchData(
    `/api/venues/${venueId}/shows?date=${formattedDate}`,
  );

  return response.json();
};

export const getShowDates = async (
  year: number,
  month: number,
): Promise<HasShowDates> => {
  const date = `${year}${month}`;
  const response = await fetchData(`/api/shows/calendar?date=${date}`);

  return response.json();
};
