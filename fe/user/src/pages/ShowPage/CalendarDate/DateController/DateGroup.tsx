import styled from '@emotion/styled';
import { equalDates, getKoreanWeekdayName } from '~/utils/dateUtils';

type Props = {
  dates: Date[];
  showDates: number[];
  selectedDate: Date;
  selectDate: (date: Date) => void;
};

export const DateGroup: React.FC<Props> = ({
  dates,
  showDates,
  selectedDate,
  selectDate,
}) => {
  const today = new Date();

  return (
    <StyledDateGroup>
      {dates.map((date: Date) => {
        const day = getKoreanWeekdayName(date.getDay());
        const dateNumber = date.getDate();

        return (
          <li key={dateNumber}>
            <StyledDateInfo
              $selected={equalDates(date, selectedDate)}
              onClick={() => selectDate(date)}
              disabled={!showDates.includes(dateNumber)}
            >
              <StyledDay>{equalDates(date, today) ? '오늘' : day}</StyledDay>
              <StyledDate>{dateNumber}</StyledDate>
            </StyledDateInfo>
          </li>
        );
      })}
    </StyledDateGroup>
  );
};

const StyledDateGroup = styled.ul`
  width: 100%;
  display: flex;
  justify-content: space-around;
`;

const StyledDateInfo = styled.button<{ $selected: boolean }>`
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

  &:disabled {
    pointer-events: none;

    > p {
      color: #e4e4e4;
    }
  }

  > p {
    color: ${({ $selected }) => ($selected ? '#FF5019' : '')};
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
