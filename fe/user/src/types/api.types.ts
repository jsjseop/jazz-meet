import { Inquiry, InquiryCategories } from './inquiry.types';
import { Coordinate } from './map.types';

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
} & ShowTime;

export type HasShowDates = {
  hasShow: number[];
};

export type ShowDetail = {
  id: number;
  posterUrl: string;
  teamName: string;
  description: string;
  startTime: string;
  endTime: string;
};

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
  venues: VenueData[];
  totalCount: number;
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
} & Coordinate;

export type ShowTime = {
  startTime: string;
  endTime: string;
};

export type SearchSuggestion = {
  id: number;
  name: string;
  address: string;
} & Coordinate;

export type VenueDetailData = {
  id: number;
  images: {
    id: number;
    url: string;
  }[];
  name: string;
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
} & Coordinate;

export type ShowRegion = {
  region: string;
  venues: ShowVenue[];
};

export type ShowVenue = {
  id: number;
  name: string;
  shows: Omit<ShowDetail, 'description'>[];
};

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
