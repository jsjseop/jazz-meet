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
