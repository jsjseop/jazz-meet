import { useState } from 'react';

export const useCalendar = () => {
  const [calendarDate, setCalendarDate] = useState(() => new Date());
  const [selectedDate, setSelectedDate] = useState(() => new Date());

  const goToPreviousMonth = () => {
    calendarDate.setMonth(calendarDate.getMonth() - 1);
    setCalendarDate(new Date(calendarDate));
  };

  const goToNextMonth = () => {
    calendarDate.setMonth(calendarDate.getMonth() + 1);
    setCalendarDate(new Date(calendarDate));
  };

  const selectDate = (date: Date) => {
    setSelectedDate(date);
  };

  const selectPreviousDate = () => {
    selectedDate.setDate(selectedDate.getDate() - 1);
    const newDate = new Date(selectedDate);
    setCalendarDate(newDate);
    setSelectedDate(newDate);
  };

  const selectNextDate = () => {
    selectedDate.setDate(selectedDate.getDate() + 1);
    const newDate = new Date(selectedDate);
    setCalendarDate(newDate);
    setSelectedDate(newDate);
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
