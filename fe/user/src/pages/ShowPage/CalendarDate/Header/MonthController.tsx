import styled from '@emotion/styled';
import CaretLeft from '~/assets/icons/CaretLeft.svg?react';
import CaretRight from '~/assets/icons/CaretRight.svg?react';

export type MonthControllerProps = {
  calendarDate: Date;
  selectedDate: Date;
  goToPreviousMonth: () => void;
  goToNextMonth: () => void;
  selectDate: (date: Date) => void;
};

export const MonthController: React.FC<MonthControllerProps> = ({
  calendarDate,
  selectedDate,
  goToPreviousMonth,
  goToNextMonth,
  selectDate,
}) => {
  const currentYear = calendarDate.getFullYear();
  const currentMonth = calendarDate.getMonth() + 1;

  const selectedYear = selectedDate.getFullYear();
  const selectedMonth = selectedDate.getMonth() + 1;

  const monthText = `${currentYear}.${currentMonth}`;

  const onPreviousMonthClick = () => {
    goToPreviousMonth();
    selectDate(new Date(selectedYear, selectedMonth - 1, 0));
  };

  const onNextMonthClick = () => {
    goToNextMonth();
    selectDate(new Date(selectedYear, selectedMonth, 1));
  };

  return (
    <StyledMonthController>
      <CaretLeft onClick={onPreviousMonthClick} />
      <MonthText>{monthText}</MonthText>
      <CaretRight onClick={onNextMonthClick} />
    </StyledMonthController>
  );
};

const StyledMonthController = styled.div`
  display: flex;
  align-items: center;
  gap: 30px;

  > svg {
    width: 52px;
    height: 66px;
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

const MonthText = styled.h2`
  color: #1b1b1b;
  font-size: 46px;
  font-weight: 700;
`;
