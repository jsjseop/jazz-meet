import styled from '@emotion/styled';
import CaretLeft from '~/assets/icons/CaretLeft.svg?react';
import CaretRight from '~/assets/icons/CaretRight.svg?react';
import { DateGroup } from './DateGroup';

export const DateController: React.FC = () => {
  const goToPreviousGroup = () => {};
  const goToNextGroup = () => {};

  return (
    <StyledDateContainer>
      <StyledArrowButton>
        <CaretLeft onClick={goToPreviousGroup} />
      </StyledArrowButton>
      <DateGroup />
      <StyledArrowButton>
        <CaretRight onClick={goToNextGroup} />
      </StyledArrowButton>
    </StyledDateContainer>
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
