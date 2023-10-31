import { useState } from 'react';

export const useCalendar = () => {
  const [calendarDate, setCalendarDate] = useState(() => new Date());
  const [selectedDate, setSelectedDate] = useState(() => new Date());

  const goToPreviousMonth = () => {
    calendarDate.setDate(1);
    calendarDate.setMonth(calendarDate.getMonth() - 1);
    setCalendarDate(new Date(calendarDate));
  };

  const goToNextMonth = () => {
    calendarDate.setDate(1);
    calendarDate.setMonth(calendarDate.getMonth() + 1);
    setCalendarDate(new Date(calendarDate));
  };

  const selectDate = (date: Date) => {
    setSelectedDate(date);
  };

  const selectPreviousDate = () => {
    selectedDate.setDate(selectedDate.getDate() - 1);
    setCalendarDate(new Date(selectedDate));
    setSelectedDate(new Date(selectedDate));
  };

  const selectNextDate = () => {
    selectedDate.setDate(selectedDate.getDate() + 1);
    setCalendarDate(new Date(selectedDate));
    setSelectedDate(new Date(selectedDate));
  };

  return {
    calendarDate,
    selectedDate,
    goToPreviousMonth,
    goToNextMonth,
    selectDate,
    selectPreviousDate,
    selectNextDate,
  };
};
