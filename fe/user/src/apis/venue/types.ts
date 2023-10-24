import { Coordinate } from 'types/map';

export type GetAroundVenuesResponse = {
  id: number;
  thumbnailUrl: string;
  name: string;
  address: string;
};

export type Pin = {
  id: number;
  name: string;
} & Coordinate;
