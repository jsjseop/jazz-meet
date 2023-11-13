import styled from '@emotion/styled';
import { useEffect, useState } from 'react';
import CaretLeft from '~/assets/icons/CaretLeft.svg?react';
import CaretRight from '~/assets/icons/CaretRight.svg?react';
import { getFirstDay, getLastDate, getMonthDates } from '~/utils/dateUtils';
import { DateGroup } from './DateGroup';

export type DateData = {
  date: number;
  day: string;
};

type Props = {
  calendarDate: Date;
  selectedDate: Date;
  selectDate: (date: Date) => void;
};

export const DateController: React.FC<Props> = ({
  calendarDate,
  selectedDate,
  selectDate,
}) => {
  const [datesInMonth, setDatesInMonth] = useState<DateData[]>([]);
  const [page, setPage] = useState();

  const currentDateGroup = getCurrentDateGroup(
    datesInMonth,
    selectedDate.getDate() - 1,
    page,
  );

  useEffect(() => {
    const currentYear = calendarDate.getFullYear();
    const currentMonth = calendarDate.getMonth() + 1;

    const firstDay = getFirstDay(currentYear, currentMonth);
    const lastDate = getLastDate(currentYear, currentMonth);

    const dates = getMonthDates(firstDay, lastDate);

    setDatesInMonth(dates);
  }, [calendarDate]);

  const goToPreviousGroup = () => {};
  const goToNextGroup = () => {};

  return (
    <StyledDateContainer>
      <StyledArrowButton>
        <CaretLeft onClick={goToPreviousGroup} />
      </StyledArrowButton>
      <DateGroup dates={currentDateGroup} selectedDate={selectedDate} />
      <StyledArrowButton>
        <CaretRight onClick={goToNextGroup} />
      </StyledArrowButton>
    </StyledDateContainer>
  );
};

const getCurrentDateGroup = (
  datesInMonth: DateData[],
  dateIndex?: number,
  page?: number,
) => {
  if (typeof dateIndex !== 'undefined' && dateIndex !== 0) {
    return dateIndex < 7
      ? datesInMonth.slice(0, 13)
      : datesInMonth.length - dateIndex < 7
      ? datesInMonth.slice(datesInMonth.length - 13, datesInMonth.length)
      : datesInMonth.slice(dateIndex - 6, dateIndex + 7);
  }

  if (typeof page !== 'undefined' && page >= 0 && page < 3) {
    return datesInMonth.slice(page * 13, (page + 1) * 13);
  }

  return datesInMonth.slice(0, 13);
};

const StyledDateContainer = styled.div`
  position: relative;
  margin-top: 10px;
  padding: 10px 0;
  display: flex;
  align-items: center;
`;

const StyledArrowButton = styled.button`
  position: absolute;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;

  &:first-of-type {
    left: 0;
    transform: translateX(-100%);
  }

  &:last-of-type {
    right: 0;
    transform: translateX(100%);
  }

  > svg {
    width: 32px;
    height: 32px;
    fill: #a3a4a9;

    &:hover {
      cursor: pointer;
      opacity: 0.7;
    }

    &:active {
      opacity: 0.5;
    }
  }
`;
