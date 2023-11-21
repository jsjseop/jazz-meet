import styled from '@emotion/styled';
import { useEffect, useRef, useState } from 'react';
import { getShowDates, getShowsByDate } from '~/apis/show';
import CaretLeft from '~/assets/icons/CaretLeft.svg?react';
import CaretRight from '~/assets/icons/CaretRight.svg?react';
import { DATE_BUTTON_WIDTH } from '~/constants/ELEMENT_SIZE';
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
  const dateGroupRef = useRef<HTMLDivElement>(null);

  const datePerWidth =
    dateGroupRef.current === null
      ? 1
      : dateGroupRef.current.offsetWidth / DATE_BUTTON_WIDTH;

  const goToPreviousGroup = () => {
    if (!dateGroupRef.current) {
      return;
    }

    setCenterDateIndex((p) => getCenterDateIndex(p - datePerWidth));
  };
  const goToNextGroup = () => {
    if (!dateGroupRef.current) {
      return;
    }

    setCenterDateIndex((p) =>
      getCenterDateIndex(p + datePerWidth, datesInMonth.length - 1),
    );
  };
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
        dateGroupRef={dateGroupRef}
        dates={datesInMonth}
        centerDateIndex={centerDateIndex}
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

const getCenterDateIndex = (index: number, lastIndex?: number) => {
  if (index < 0) {
    return 0;
  }

  if (lastIndex && index > lastIndex) {
    return lastIndex;
  }

  return index;
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

  @media screen and (max-width: 1264px) {
    position: static;

    &:first-of-type {
      transform: none;
    }

    &:last-of-type {
      right: 0;
      transform: none;
    }
  }
`;
