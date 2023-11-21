import styled from '@emotion/styled';
import { useEffect, useRef } from 'react';
import { DATE_BUTTON_WIDTH } from '~/constants/ELEMENT_SIZE';
import { equalDates, getKoreanWeekdayName } from '~/utils/dateUtils';

type Props = {
  dateGroupRef: React.RefObject<HTMLDivElement>;
  dates: Date[];
  centerDateIndex: number;
  showDates: number[];
  selectedDate: Date;
  selectDate: (date: Date) => void;
};

export const DateGroup: React.FC<Props> = ({
  dateGroupRef,
  dates,
  centerDateIndex,
  showDates,
  selectedDate,
  selectDate,
}) => {
  const dateContainerRef = useRef<HTMLUListElement>(null);
  const today = new Date();

  useEffect(() => {
    if (dateGroupRef.current && dateContainerRef.current) {
      const dateGroupWidth = dateGroupRef.current.offsetWidth;
      const dateContainerWidth = dateContainerRef.current.scrollWidth;

      const translateX =
        dateGroupWidth / 2 - centerDateIndex * DATE_BUTTON_WIDTH;
      const scrollX =
        translateX > 0
          ? 0
          : translateX + dateContainerWidth - dateGroupWidth < 0
          ? dateGroupWidth - dateContainerWidth
          : translateX;

      dateContainerRef.current.style.transform = `translateX(${scrollX}px)`;
    }
  }, [centerDateIndex, dateGroupRef]);

  return (
    <StyledDateGroup ref={dateGroupRef}>
      <StyledDateContainer ref={dateContainerRef}>
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
      </StyledDateContainer>
    </StyledDateGroup>
  );
};

const StyledDateGroup = styled.div`
  flex: 1;
  overflow: hidden;
`;

const StyledDateContainer = styled.ul`
  display: flex;
  transition: transform 0.3s ease-in-out;
`;

const StyledDateInfo = styled.button<{ $selected: boolean }>`
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 92px;

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
