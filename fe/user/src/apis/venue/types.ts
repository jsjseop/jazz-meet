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

export type GetVenuesByKeywordResponse = {
  venues: VenueData[];
  venueCount: number;
  currentPage: number;
  maxPage: number;
}

export type VenueData = {
  id: number;
  thumbnailUrl: string;
  name: string;
  address: string;
  description: string;
  showInfo: ShowTime[];
  venueCount: number;
  currentPage: number;
  maxPage: number;
}

export type ShowTime = {
  startTime: string;
  endTime: string;
}