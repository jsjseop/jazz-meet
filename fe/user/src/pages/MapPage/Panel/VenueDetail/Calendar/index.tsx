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
        <ChevronLeftIcon onClick={prevMonth} sx={{ fill: '#B5BEC6' }} />
        <StyledCalendarHeaderTitle>
          {currentDate.toLocaleString('en-US', { month: 'long' })} {currentYear}
        </StyledCalendarHeaderTitle>
        <ChevronRightIcon onClick={nextMonth} sx={{ fill: '#B5BEC6' }} />
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
          <div key={index}></div>
        ))}
        {Array.from({ length: lastDay }).map((_, index) => (
          <div key={index}>{index + 1}</div>
        ))}
      </StyledDaysGrid>
    </StyledCalendar>
  );
};

const StyledCalendar = styled.div`
  min-width: 300px;

  border: 1px solid #dbdbdb;
  border-radius: 8px;
  padding: 24px;
  display: flex;
  flex-direction: column;
  gap: 22px;
  box-sizing: border-box;
`;

const StyledCalendarHeader = styled.div`
  font-size: 14px;
  font-weight: 600;
  display: flex;
  align-items: center;
  justify-content: space-between;
`;

const StyledCalendarHeaderTitle = styled.div`
  color: #4a5660;
`;

const StyledDaysOfTheWeek = styled.div`
  color: #b5bec6;
  font-size: 10px;
  letter-spacing: 1.5px;

  display: grid;
  grid-template-columns: repeat(7, 1fr);
  align-items: center;
  justify-items: center;
`;

const StyledDaysGrid = styled.div`
  position: relative;
  color: #4a5660;
  display: grid;
  grid-template-columns: repeat(7, 1fr);
  grid-template-rows: repeat(5, 1fr);
  align-items: center;
  justify-items: center;
  gap: 10px;

  > div {
    padding: 50% 0;
    height: 0;
    position: relative;
    top: -20%;
  }
`;
