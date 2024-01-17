import { create } from 'zustand';
import { ShowDetail } from '~/types/api.types';

type ShowDetailState = {
  showDetails: ShowDetail[];
  setShowDetails: (showDetails: ShowDetail[]) => void;
};

export const useShowDetailStore = create<ShowDetailState>((set) => ({
  showDetails: [],
  setShowDetails: (showDetails: ShowDetail[]) => set({ showDetails }),
}));
