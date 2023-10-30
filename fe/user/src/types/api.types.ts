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
