import styled from '@emotion/styled';
import { MonthController, MonthControllerProps } from './MonthController';

export const Header: React.FC<MonthControllerProps> = ({ ...props }) => {
  return (
    <StyledHeader>
      <MonthController {...props} />
      {/* <DatePicker /> */}
    </StyledHeader>
  );
};

const StyledHeader = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
`;
