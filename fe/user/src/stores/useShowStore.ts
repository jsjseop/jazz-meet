import { create } from 'zustand';
import { ShowRegion } from '~/types/api.types';

type ShowState = {
  showsAtDate: ShowRegion[];
  setShowsAtDate: (showsAtDate: ShowRegion[]) => void;
};

export const useShowStore = create<ShowState>((set) => ({
  showsAtDate: [],
  setShowsAtDate: (showsAtDate: ShowRegion[]) => set(() => ({ showsAtDate })),
}));
