import { fetchData } from 'apis/fetchData';
import { GetAroundVenuesResponse, Pin } from './types';
import { getQueryString } from '@utils/getQueryString';
import { Coordinate } from 'types/map';

export const getAroundVenues = async (
  coordinate: Coordinate,
): Promise<GetAroundVenuesResponse> => {
  const queryString = getQueryString(coordinate);
  const response = await fetchData(`/api/venues/around-venues?${queryString}`);

  return response.json();
};

export const searchVenuePins = async (word: string): Promise<Pin[]> => {
  const response = await fetchData(`/api/venues/pins/search?word=${word}`);

  return response.json();
};

export const getAroundVenuePins = async (
  coordinate: Coordinate,
): Promise<Pin[]> => {
  const queryString = getQueryString(coordinate);
  const response = await fetchData(
    `/api/venues/pins/around-venues?${queryString}`,
  );

  return response.json();
};

export const getVenuePinsOnMap = async (
  coordinate: Coordinate,
): Promise<Pin[]> => {
  const queryString = getQueryString(coordinate);
  const response = await fetchData(
    `/api/venues/pins/around-venues?${queryString}`,
  );

  return response.json();
};
