export type Coordinate = {
  latitude: number;
  longitude: number;
};

export type CoordinateBoundary = {
  lowLatitude: number;
  highLatitude: number;
  lowLongitude: number;
  highLongitude: number;
};

export type Pin = {
  id: number;
  name: string;
} & Coordinate;
