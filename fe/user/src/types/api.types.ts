import { Coordinate } from 'types/map.types';

export type AroundVenue = {
  id: number;
  thumbnailUrl: string;
  name: string;
  address: string;
} & Coordinate;

export type Pin = {
  id: number;
  name: string;
} & Coordinate;

export type UpcomingShow = {
  venueId: number;
  showId: number;
  posterUrl: string;
  showName: string;
  startTime: string;
  endTime: string;
};

export type SearchParams = {
  word?: string;
  page?: number;
};

export type SearchedVenues = {
  venues: VenueData[];
  venueCount: number;
  currentPage: number;
  maxPage: number;
};

export type VenueData = {
  id: number;
  thumbnailUrl: string;
  name: string;
  address: string;
  description: string;
  showInfo: ShowTime[];
  latitude: number;
  longitude: number;
};

export type ShowTime = {
  startTime: string;
  endTime: string;
};
