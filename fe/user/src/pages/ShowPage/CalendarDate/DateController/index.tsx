import styled from '@emotion/styled';
import { useEffect, useState } from 'react';
import { getShowDates, getShowsByDate } from '~/apis/show';
import CaretLeft from '~/assets/icons/CaretLeft.svg?react';
import CaretRight from '~/assets/icons/CaretRight.svg?react';
import { useShowStore } from '~/stores/useShowStore';
import { getFormattedYearMonth, getMonthDates } from '~/utils/dateUtils';
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
  const [showDates, setShowDates] = useState<number[]>([]);
  const { setShowsAtDate } = useShowStore((state) => ({
    setShowsAtDate: state.setShowsAtDate,
  }));

  const currentDateGroup = getCurrentDateGroup(datesInMonth, centerDateIndex);

  const goToPreviousGroup = () =>
    setCenterDateIndex((p) => getCenterDateIndex(p - 9));
  const goToNextGroup = () =>
    setCenterDateIndex((p) => getCenterDateIndex(p + 9));

  useEffect(() => {
    const updateShowDates = async () => {
      const datesData = await getShowDates(selectedDate);
      const showDates = datesData.hasShow;

      try {
        const closestShowDate = getClosestShowDate(showDates, selectedDate);

        selectDate(closestShowDate);
      } catch (e) {
        console.error(e);
        selectDate(
          new Date(
            selectedDate.getFullYear(),
            selectedDate.getMonth(),
            selectedDate.getDate(),
          ),
        );
      }

      setShowDates(showDates);
    };

    updateShowDates();
    setDatesInMonth(getMonthDates(selectedDate));
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [selectedDate.getMonth()]);

  useEffect(() => {
    const updateShowsAtDate = async () => {
      const showsAtDate = await getShowsByDate(selectedDate);

      setShowsAtDate(showsAtDate);
    };

    updateShowsAtDate();
    setCenterDateIndex(selectedDate.getDate() - 1);
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [selectedDate]);

  return (
    <StyledDateContainer>
      <StyledArrowButton>
        <CaretLeft onClick={goToPreviousGroup} />
      </StyledArrowButton>
      <DateGroup
        dates={currentDateGroup}
        showDates={showDates}
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

const getClosestShowDate = (dates: number[], date: Date) => {
  const dateNumber = date.getDate();
  const showDates = [...dates].sort((prev, next) => prev - next);

  if (showDates.includes(dateNumber)) {
    return date;
  }

  if (showDates.length === 0) {
    throw new Error(`No show date found in ${getFormattedYearMonth(date)}`);
  }

  const showDateIndex = showDates.findIndex(
    (showDate) => showDate > dateNumber,
  );

  if (showDateIndex === -1) {
    return new Date(
      date.getFullYear(),
      date.getMonth(),
      showDates[showDates.length - 1],
    );
  }

  return new Date(
    date.getFullYear(),
    date.getMonth(),
    showDates[showDateIndex],
  );
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
