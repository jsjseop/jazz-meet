import styled from '@emotion/styled';
import { useEffect, useState } from 'react';
import CaretLeft from '~/assets/icons/CaretLeft.svg?react';
import CaretRight from '~/assets/icons/CaretRight.svg?react';
import { getMonthDates } from '~/utils/dateUtils';
import { DateGroup } from './DateGroup';

type Props = {
  selectedDate: Date;
  selectDate: (date: Date) => void;
};

export const DateController: React.FC<Props> = ({
  selectedDate,
  selectDate,
}) => {
  const [datesInMonth, setDatesInMonth] = useState<Date[]>([]);
  const [centerDateIndex, setCenterDateIndex] = useState(0);

  const currentDateGroup = getCurrentDateGroup(datesInMonth, centerDateIndex);

  const goToPreviousGroup = () =>
    setCenterDateIndex((p) => getCenterDateIndex(p - 9));
  const goToNextGroup = () =>
    setCenterDateIndex((p) => getCenterDateIndex(p + 9));

  useEffect(() => {
    setDatesInMonth(getMonthDates(selectedDate));
    setCenterDateIndex(selectedDate.getDate() - 1);
  }, [selectedDate]);

  return (
    <StyledDateContainer>
      <StyledArrowButton>
        <CaretLeft onClick={goToPreviousGroup} />
      </StyledArrowButton>
      <DateGroup
        dates={currentDateGroup}
        selectedDate={selectedDate}
        selectDate={selectDate}
      />
      <StyledArrowButton>
        <CaretRight onClick={goToNextGroup} />
      </StyledArrowButton>
    </StyledDateContainer>
  );
};

const getCurrentDateGroup = (datesInMonth: Date[], dateIndex: number) => {
  if (dateIndex < 7) {
    return datesInMonth.slice(0, 13);
  }

  if (datesInMonth.length - dateIndex < 7) {
    return datesInMonth.slice(datesInMonth.length - 13, datesInMonth.length);
  }

  return datesInMonth.slice(dateIndex - 6, dateIndex + 7);
};

const getCenterDateIndex = (index: number) => {
  if (index < 7) {
    return 6;
  }

  if (index < 16) {
    return 15;
  }

  return 24;
};

const StyledDateContainer = styled.div`
  position: relative;
  margin-top: 10px;
  padding: 10px 0;
  display: flex;
  align-items: center;
`;

const StyledArrowButton = styled.button`
  position: absolute;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;

  &:first-of-type {
    left: 0;
    transform: translateX(-100%);
  }

  &:last-of-type {
    right: 0;
    transform: translateX(100%);
  }

  > svg {
    width: 32px;
    height: 32px;
    fill: #a3a4a9;

    &:hover {
      cursor: pointer;
      opacity: 0.7;
    }

    &:active {
      opacity: 0.5;
    }
  }
`;
