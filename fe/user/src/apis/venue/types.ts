import { Coordinate } from 'types/map';

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
