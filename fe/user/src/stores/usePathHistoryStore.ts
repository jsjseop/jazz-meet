import { create } from 'zustand';

type PathHistoryStore = {
  previousPath: string;
  setPreviousPath: (previousPath: string) => void;
};

export const usePathHistoryStore = create<PathHistoryStore>((set) => ({
  previousPath: '',
  setPreviousPath: (previousPath: string) => set(() => ({ previousPath })),
}));
