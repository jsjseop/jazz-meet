import { create } from 'zustand';

type ShowDetailState = {
  showId: number;
  showDate: Date;
  setShowId: (showId: number) => void;
  setShowDate: (showDate: Date) => void;
};

export const useShowDetailStore = create<ShowDetailState>((set) => ({
  showId: 0,
  showDate: new Date(),
  setShowId: (showId: number) => set({ showId }),
  setShowDate: (showDate: Date) => set({ showDate }),
}));
