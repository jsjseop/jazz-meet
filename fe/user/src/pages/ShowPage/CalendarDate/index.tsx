import styled from '@emotion/styled';
import { useCalendar } from '~/pages/MapPage/Panel/VenueDetail/RestInfo/ShowInfo/useCalendar';
import { DateController } from './DateController';
import { Header } from './Header';

export const CalendarDate: React.FC = () => {
  const {
    calendarDate,
    selectedDate,
    goToPreviousMonth,
    goToNextMonth,
    selectDate,
  } = useCalendar();

  return (
    <StyledCalendarDate>
      <Header
        calendarDate={calendarDate}
        goToPreviousMonth={goToPreviousMonth}
        goToNextMonth={goToNextMonth}
      />
      <DateController
        calendarDate={calendarDate}
        selectedDate={selectedDate}
        selectDate={selectDate}
      />
    </StyledCalendarDate>
  );
};

const StyledCalendarDate = styled.div`
  width: 100%;
`;
