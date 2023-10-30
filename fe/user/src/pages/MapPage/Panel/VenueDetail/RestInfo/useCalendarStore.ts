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
    set(({ currentDate }) => {
      currentDate.setMonth(currentDate.getMonth() - 1);

      return {
        currentDate: new Date(currentDate),
      };
    }),
  nextMonth: () =>
    set(({ currentDate }) => {
      currentDate.setMonth(currentDate.getMonth() + 1);

      return {
        currentDate: new Date(currentDate),
      };
    }),
}));
