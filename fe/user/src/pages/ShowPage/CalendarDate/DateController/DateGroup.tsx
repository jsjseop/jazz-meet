import styled from '@emotion/styled';
import { equalDates, getKoreanWeekdayName, isToday } from '~/utils/dateUtils';

type Props = {
  dates: Date[];
  selectedDate: Date;
};

export const DateGroup: React.FC<Props> = ({ dates, selectedDate }) => {
  return (
    <StyledDateGroup>
      {dates.map((date: Date) => {
        const day = getKoreanWeekdayName(date.getDay());
        const dateNumber = date.getDate();

        return (
          <StyledDateInfo
            key={dateNumber}
            $active={equalDates(date, selectedDate)}
          >
            <StyledDay>{isToday(date) ? '오늘' : day}</StyledDay>
            <StyledDate>{dateNumber}</StyledDate>
          </StyledDateInfo>
        );
      })}
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
