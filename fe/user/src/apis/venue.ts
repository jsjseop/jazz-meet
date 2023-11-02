import {
  AroundVenue,
  Pin,
  SearchParams,
  SearchedVenues,
} from '~/types/api.types';
import { Coordinate, CoordinateBoundary } from '~/types/map.types';
import { getQueryString } from '~/utils/getQueryString';
import { fetchData } from './fetchData';

export const getAroundVenues = async (
  coordinate: Coordinate,
): Promise<AroundVenue[]> => {
  const queryString = getQueryString(coordinate);
  const response = await fetchData(`/api/venues/around-venues${queryString}`);

  return response.json();
};

export const getVenuePinsBySearch = async (word: string): Promise<Pin[]> => {
  const response = await fetchData(`/api/venues/pins/search${word}`);

  return response.json();
};

export const getVenuePinsByMapBounds = async (bounds: string): Promise<Pin[]> => {
  const response = await fetchData(`/api/venues/pins/map${bounds}`);

  return response.json();
};

export const getAroundVenuePins = async (
  coordinate: Coordinate,
): Promise<Pin[]> => {
  const queryString = getQueryString(coordinate);
  const response = await fetchData(
    `/api/venues/pins/around-venues${queryString}`,
  );

  return response.json();
};

export const getVenuePinsOnMap = async (
  coordinateBoundary: CoordinateBoundary,
): Promise<Pin[]> => {
  const queryString = getQueryString(coordinateBoundary);
  const response = await fetchData(`/api/venues/pins/map${queryString}`);

  return response.json();
};

export const getVenuesByKeyword = async (
  searchParams: SearchParams = {},
): Promise<SearchedVenues> => {
  const queryString = getQueryString(searchParams);
  const response = await fetchData(`/api/venues/search${queryString}`);

  return response.json();
};
