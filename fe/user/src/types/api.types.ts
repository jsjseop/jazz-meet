import { Inquiry, InquiryCategories } from './inquiry.types';
import { Coordinate } from './map.types';

type Venue = {
  id: number;
  name: string;
  address: string;
};

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
  venueHours: {
    day: string;
    businessHours: string;
  }[];
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
  id: number;
  posterUrl: string;
  teamName: string;
  description: string;
} & ShowTime;

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

type ShowTime = {
  startTime: string;
  endTime: string;
};

export type SearchSuggestion = Venue & Coordinate;

export type ShowRegion = {
  region: string;
  venues: ShowVenue[];
};

type ShowVenue = {
  shows: Omit<ShowDetail, 'description'>[];
} & Omit<Venue, 'address'>;

export type GetInquiryParams = {
  category?: InquiryCategories;
  word?: string;
  page?: number;
};

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
  content: string;
  answer: {
    id: number;
    content: string;
    createdAt: string;
    modifiedAt: string;
  };
};

export type PostInquiryParams = {
  category: InquiryCategories;
  nickname: string;
  password: string;
  content: string;
};
