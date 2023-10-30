import { create } from 'zustand';

type CalendarStore = {
  currentDate: Date;
  prevMonth: () => void;
  nextMonth: () => void;
};

const initialState = {
  currentDate: new Date(),
};

export const useCalendarStore = create<CalendarStore>()((set) => ({
  ...initialState,
  prevMonth: () =>
    set(({ currentDate }) => ({
      currentDate: new Date(currentDate.getMonth() - 1),
    })),
  nextMonth: () =>
    set(({ currentDate }) => ({
      currentDate: new Date(currentDate.getMonth() + 1),
    })),
}));
