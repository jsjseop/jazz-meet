import { create } from 'zustand';
import { Coordinate } from '~/types/map.types';

type UserCoordinateStore = {
  userCoordinate: Coordinate | null;
  setUserCoordinate: (userCoordinate: Coordinate) => void;
};

const initialState = {
  userCoordinate: null,
};

export const useUserCoordinateStore = create<UserCoordinateStore>()((set) => ({
  ...initialState,
  setUserCoordinate: (userCoordinate: Coordinate) =>
    set(() => ({ userCoordinate })),
}));
