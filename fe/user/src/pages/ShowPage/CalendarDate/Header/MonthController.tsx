import styled from '@emotion/styled';
import CaretLeft from '~/assets/icons/CaretLeft.svg?react';
import CaretRight from '~/assets/icons/CaretRight.svg?react';

export type MonthControllerProps = {
  calendarDate: Date;
  goToPreviousMonth: () => void;
  goToNextMonth: () => void;
};

export const MonthController: React.FC<MonthControllerProps> = ({
  calendarDate,
  goToPreviousMonth,
  goToNextMonth,
}) => {
  const currentYear = calendarDate.getFullYear();
  const currentMonth = calendarDate.getMonth() + 1;

  const monthText = `${currentYear}.${currentMonth}`;

  return (
    <StyledMonthController>
      <CaretLeft onClick={goToPreviousMonth} />
      <MonthText>{monthText}</MonthText>
      <CaretRight onClick={goToNextMonth} />
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
