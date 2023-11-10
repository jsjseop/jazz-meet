import styled from '@emotion/styled';
import { MonthController } from './MonthController';

export const Header: React.FC = () => {
  return (
    <StyledHeader>
      <MonthController />
      {/* <DatePicker /> */}
    </StyledHeader>
  );
};

const StyledHeader = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
`;
