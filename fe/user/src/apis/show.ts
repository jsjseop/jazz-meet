import { HasShowDates, ShowDetail, UpcomingShow } from '~/types/api.types';
import { fetchData } from './fetchData';
import { getFormattedDate, getFormattedYearMonth } from '~/utils/dateUtils';

export const getUpcomingShows = async (): Promise<UpcomingShow[]> => {
  const response = await fetchData(`/api/shows/upcoming`);

  return response.json();
};

export const getHasShowDates = async ({
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

export const getShowsByDate = async ({
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
