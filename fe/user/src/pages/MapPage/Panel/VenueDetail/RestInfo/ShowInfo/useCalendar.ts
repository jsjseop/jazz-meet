import { useEffect, useState } from 'react';
import { useSearchParams } from 'react-router-dom';
import { getFormattedDate } from '~/utils/dateUtils';

export const useCalendar = (initialDate?: Date) => {
  const [calendarDate, setCalendarDate] = useState(
    () => initialDate ?? new Date(),
  );
  const [selectedDate, setSelectedDate] = useState(
    () => initialDate ?? new Date(),
  );
  const [, setSearchParams] = useSearchParams();

  const goToPreviousMonth = () => {
    calendarDate.setDate(1); // calendarDate가 31일일 때, 2개월 넘어가는 현상 방지
    calendarDate.setMonth(calendarDate.getMonth() - 1);
    setCalendarDate(new Date(calendarDate));
  };

  const goToNextMonth = () => {
    calendarDate.setDate(1); // calendarDate가 31일일 때, 2개월 넘어가는 현상 방지
    calendarDate.setMonth(calendarDate.getMonth() + 1);
    setCalendarDate(new Date(calendarDate));
  };

  const selectDate = (date: Date) => {
    setSearchParams({ date: getFormattedDate(date) });
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

  useEffect(() => {
    if (initialDate) {
      setSelectedDate(initialDate);
    }
  }, [initialDate]);

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
