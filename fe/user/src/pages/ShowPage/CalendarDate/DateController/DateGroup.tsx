import styled from '@emotion/styled';
import { DateData } from '.';

type Props = {
  dates: DateData[];
  selectedDate: Date;
};

export const DateGroup: React.FC<Props> = ({ dates, selectedDate }) => {
  const today = new Date().getDate();
  const selectedDateInMonth = selectedDate.getDate();

  return (
    <StyledDateGroup>
      {dates.map(({ day, date }) => (
        <StyledDateInfo key={date} $active={selectedDateInMonth === date}>
          <StyledDay>{date === today ? '오늘' : day}</StyledDay>
          <StyledDate>{date}</StyledDate>
        </StyledDateInfo>
      ))}
    </StyledDateGroup>
  );
};

const StyledDateGroup = styled.div`
  width: 100%;
  display: flex;
  justify-content: space-around;
`;

const StyledDateInfo = styled.div<{ $active: boolean }>`
  display: flex;
  flex-direction: column;
  align-items: center;
  min-width: 24px;

  &:hover {
    cursor: pointer;
    opacity: 0.7;
  }

  &:active {
    opacity: 0.5;
  }

  > p {
    ${({ $active }) => $active && `color: #FF5019`}
  }
`;

const StyledDay = styled.p`
  padding: 6px 0;
  font-size: 18px;
  font-weight: 500;
  line-height: 1.4;
  color: #a3a4a9;
`;

const StyledDate = styled.p`
  padding: 6px 0;
  font-size: 28px;
  font-weight: 700;
  color: #686970;
`;
