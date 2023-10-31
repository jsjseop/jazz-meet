import { useState } from 'react';

export const useCalendar = () => {
  const [currentDate, setCurrentDate] = useState(() => new Date());
  const [selectedDate, setSelectedDate] = useState(() => new Date());

  const prevMonth = () => {
    currentDate.setMonth(currentDate.getMonth() - 1);
    setCurrentDate(new Date(currentDate));
  };

  const nextMonth = () => {
    currentDate.setMonth(currentDate.getMonth() + 1);
    setCurrentDate(new Date(currentDate));
  };

  const selectDate = (date: Date) => {
    setSelectedDate(date);
  };

  return {
    currentDate,
    selectedDate,
    prevMonth,
    nextMonth,
    selectDate,
  };
};
