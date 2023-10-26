import styled from '@emotion/styled';
import ChevronLeftIcon from '@mui/icons-material/ChevronLeft';
import ChevronRightIcon from '@mui/icons-material/ChevronRight';
import { useState } from 'react';
import { getFirstDay, getLastDay } from './getFirstDayAndLastDay';

export const Calendar: React.FC = () => {
  const [currentDate, setCurrentDate] = useState(new Date());

  const nextMonth = () => {
    currentDate.setMonth(currentDate.getMonth() + 1);
    setCurrentDate(new Date(currentDate));
  };

  const prevMonth = () => {
    currentDate.setMonth(currentDate.getMonth() - 1);
    setCurrentDate(new Date(currentDate));
  };

  const currentYear = currentDate.getFullYear();
  const currentMonth = currentDate.getMonth() + 1;
  const firstDay = getFirstDay(currentYear, currentMonth);
  const lastDay = getLastDay(currentDate.getFullYear(), currentMonth);

  return (
    <StyledCalendar>
      <StyledCalendarHeader>
        <ChevronLeftIcon onClick={prevMonth} />
        <StyledCalendarHeaderTitle>
          {currentDate.toLocaleString('en-US', { month: 'long' })} {currentYear}
        </StyledCalendarHeaderTitle>
        <ChevronRightIcon onClick={nextMonth} />
      </StyledCalendarHeader>

      <StyledDaysOfTheWeek>
        <div>SAN</div>
        <div>MON</div>
        <div>TUE</div>
        <div>WED</div>
        <div>THU</div>
        <div>FRI</div>
        <div>SAT</div>
      </StyledDaysOfTheWeek>
      <StyledDaysGrid>
        {Array.from({ length: firstDay }).map((_, index) => (
          <span key={index}></span>
        ))}
        {Array.from({ length: lastDay }).map((_, index) => (
          <span key={index}>{index + 1}</span>
        ))}
      </StyledDaysGrid>
    </StyledCalendar>
  );
};

const StyledCalendar = styled.div``;

const StyledCalendarHeader = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
`;

const StyledCalendarHeaderTitle = styled.div``;
const StyledDaysOfTheWeek = styled.div`
  display: grid;
  grid-template-columns: repeat(7, 1fr);
  align-items: center;
  justify-items: center;
`;

const StyledDaysGrid = styled.div`
  display: grid;
  grid-template-columns: repeat(7, 1fr);
  grid-template-rows: repeat(5, 1fr);
  align-items: center;
  justify-items: center;
  gap: 10px;
`;
