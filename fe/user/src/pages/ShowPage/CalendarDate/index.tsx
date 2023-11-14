import styled from '@emotion/styled';
import { useCalendar } from '~/pages/MapPage/Panel/VenueDetail/RestInfo/ShowInfo/useCalendar';
import { DateController } from './DateController';
import { Header } from './Header';

export const CalendarDate: React.FC = () => {
  const { selectedDate, selectDate } = useCalendar();

  return (
    <StyledCalendarDate>
      <Header selectedDate={selectedDate} selectDate={selectDate} />
      <DateController selectedDate={selectedDate} selectDate={selectDate} />
    </StyledCalendarDate>
  );
};

const StyledCalendarDate = styled.div`
  width: 100%;
`;
