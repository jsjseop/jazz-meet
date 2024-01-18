import { create } from 'zustand';

type ShowDetailState = {
  showId: number;
  showDate: string;
  setShowId: (showId: number) => void;
  setShowDate: (showDate: string) => void;
};

export const useShowDetailStore = create<ShowDetailState>((set) => ({
  showId: 0,
  showDate: '',
  setShowId: (showId: number) => set({ showId }),
  setShowDate: (showDate: string) => set({ showDate }),
}));
