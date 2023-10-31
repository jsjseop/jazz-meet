import styled from '@emotion/styled';
import ChevronLeftIcon from '@mui/icons-material/ChevronLeft';
import ChevronRightIcon from '@mui/icons-material/ChevronRight';
import { getFirstDay, getLastDay } from './getFirstDayAndLastDay';

type Props = {
  currentDate: Date;
  selectedDate: Date;
  prevMonth: () => void;
  nextMonth: () => void;
  selectDate: (date: Date) => void;
};

export const Calendar: React.FC<Props> = ({
  currentDate,
  selectedDate,
  prevMonth,
  nextMonth,
  selectDate,
}) => {
  const currentYear = currentDate.getFullYear();
  const currentMonth = currentDate.getMonth() + 1;
  const selectedYear = selectedDate.getFullYear();
  const selectedMonth = selectedDate.getMonth() + 1;
  const selectedDay = selectedDate.getDate();
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
          <StyledDay key={index}></StyledDay>
        ))}
        {Array.from({ length: lastDay }).map((_, index) => {
          const day = index + 1;

          return (
            <StyledDay
              key={index}
              isCurrentDay={
                currentYear === selectedYear &&
                currentMonth === selectedMonth &&
                day === selectedDay
              }
              onClick={() =>
                selectDate(new Date(currentYear, currentMonth - 1, day))
              }
            >
              <div>{day}</div>
            </StyledDay>
          );
        })}
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

  svg {
    cursor: pointer;
  }
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
`;

const StyledDay = styled.div<{ isCurrentDay?: boolean }>`
  width: 100%;
  padding-top: 100%;
  position: relative;
  cursor: pointer;
  ${({ isCurrentDay }) =>
    isCurrentDay &&
    `
      background-color: #F04D23;
      border-radius: 50%;
      color: #fff;
  `};

  > div {
    position: absolute;
    inset: 0;
    width: 100%;
    height: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
  }
`;
