import { UpcomingShow } from '~/types/api.types';
import { fetchData } from './fetchData';

export const getUpcomingShows = async (): Promise<UpcomingShow[]> => {
  const response = await fetchData(`/api/shows/upcoming`);

  return response.json();
};
