import { create } from 'zustand';

type ShowDetailState = {
  showDetailId: number;
  showDetailDate: Date;
  setShowDetailId: (showDetailId: number) => void;
  setShowDetailDate: (showDetailDate: Date) => void;
};

export const useShowDetailStore = create<ShowDetailState>((set) => ({
  showDetailId: 0,
  showDetailDate: new Date(),
  setShowDetailId: (showDetailId: number) => set({ showDetailId }),
  setShowDetailDate: (showDetailDate: Date) => set({ showDetailDate }),
}));
