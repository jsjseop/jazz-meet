import { Inquiry, InquiryCategories } from './inquiry.types';
import { Coordinate } from './map.types';

type Venue = {
  id: number;
  name: string;
  address: string;
};

export type VenueHour = {
  day: string;
  businessHours: string;
};

export type VenueList = {
  venues: Venue[];
} & Pagination;

export type AroundVenue = {
  thumbnailUrl: string;
} & Venue &
  Coordinate;

export type VenueItemData = {
  thumbnailUrl: string;
  description: string;
  showInfo: ShowTime[];
} & Venue &
  Coordinate;

export type VenueDetailData = {
  images: {
    id: number;
    url: string;
  }[];
  roadNameAddress: string;
  lotNumberAddress: string;
  phoneNumber: string;
  links: {
    type: string;
    url: string;
  }[];
  venueHours: VenueHour[];
  description: string;
} & Omit<Venue, 'address'> &
  Coordinate;

export type UpcomingShow = {
  venueId: number;
  showId: number;
  posterUrl: string;
  showName: string;
} & ShowTime;

export type HasShowDates = {
  hasShow: number[];
};

export type ShowDetail = {
  posterUrl: string;
  description: string;
} & Omit<Show, 'venueName'>;

type Show = {
  id: number;
  teamName: string;
  venueName: string;
} & ShowTime;

type ShowTime = {
  startTime: string;
  endTime: string;
};

export type ShowList = {
  shows: Show[];
} & Pagination;

export type SearchParams = {
  word?: string | null;
  page?: number | null;
};

export type SearchBoundsParams = {
  lowLatitude?: number | null;
  highLatitude?: number | null;
  lowLongitude?: number | null;
  highLongitude?: number | null;
  page?: number | null;
};

export type SearchedVenues = {
  venues: VenueItemData[];
} & Pagination;

export type SearchSuggestion = Venue & Coordinate;

export type VenueData = {
  id: number;
  thumbnailUrl: string;
  name: string;
  address: string;
  description: string;
  showInfo: ShowTime[];
} & Coordinate;

export type ShowRegion = {
  region: string;
  venues: ShowVenue[];
};

type ShowVenue = {
  shows: Omit<ShowDetail, 'description'>[];
} & Omit<Venue, 'address'>;

export type InquiryParams = {
  category?: InquiryCategories;
} & SearchParams;

export type InquiryData = {
  inquiries: Inquiry[];
} & Pagination;

type Pagination = {
  totalCount: number;
  currentPage: number;
  maxPage: number;
};

export type InquiryDetail = {
  id: number;
  status: string;
  nickname: string;
  createdAt: string;
  content: string;
  answer: {
    id: number;
    content: string;
    createdAt: string;
    modifiedAt: string;
  } | null;
};

export type PostInquiryParams = {
  category: InquiryCategories;
  nickname: string;
  password: string;
  content: string;
};
