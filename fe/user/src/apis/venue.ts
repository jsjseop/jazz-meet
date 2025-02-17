import {
  AroundVenue,
  SearchBoundsParams,
  SearchParams,
  SearchSuggestion,
  SearchedVenues,
  VenueDetailData,
  VenueList,
} from '~/types/api.types';
import { Coordinate, CoordinateBoundary, Pin } from '~/types/map.types';
import { getQueryString } from '~/utils/getQueryString';
import { fetchData } from './fetchData';

export const getAroundVenues = async (
  coordinate: Coordinate,
): Promise<AroundVenue[]> => {
  const queryString = getQueryString(coordinate);
  const response = await fetchData(`/api/venues/around-venues${queryString}`);

  return response.json();
};

export const getVenuePinsByWord = async (word: string): Promise<Pin[]> => {
  const response = await fetchData(`/api/venues/pins/search?word=${word}`);

  return response.json();
};

export const getVenuePinsByMapBounds = async (
  searchBoundsParams: SearchBoundsParams,
): Promise<Pin[]> => {
  const queryString = getQueryString(searchBoundsParams);
  const response = await fetchData(`/api/venues/pins/map${queryString}`);

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

export const getVenuesByWord = async (
  searchParams: SearchParams = {},
): Promise<SearchedVenues> => {
  const queryString = getQueryString(searchParams);
  const response = await fetchData(`/api/venues/search${queryString}`);

  return response.json();
};

export const getVenuesByMapBounds = async (
  searchBoundsParams: SearchBoundsParams,
): Promise<SearchedVenues> => {
  const queryString = getQueryString(searchBoundsParams);
  const response = await fetchData(`/api/venues/map${queryString}`);

  return response.json();
};

export const getSingleVenue = async (
  venueId: string,
): Promise<SearchedVenues> => {
  const response = await fetchData(`/api/venues/search/${venueId}`);

  return response.json();
};

export const getSearchSuggestions = async (
  word: string,
): Promise<SearchSuggestion[]> => {
  const response = await fetchData(`/api/search?word=${word}`);

  return response.json();
};

export const getVenueDetail = async (
  venueId: string,
): Promise<VenueDetailData> => {
  const response = await fetchData(`/api/venues/${venueId}`);

  return response.json();
};

export const getVenueList = async ({
  word,
  page,
}: SearchParams): Promise<VenueList> => {
  const queryString = getQueryString({ word, page });
  const response = await fetchData(`/api/venues${queryString}`);

  return response.json();
};
