import styled from '@emotion/styled';
import { DateController } from './DateController';
import { Header } from './Header';

export const CalendarDate: React.FC = () => {
  return (
    <StyledCalendarDate>
      <Header />
      <DateController />
    </StyledCalendarDate>
  );
};

const StyledCalendarDate = styled.div`
  width: 100%;
`;
