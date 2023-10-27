import styled from '@emotion/styled';

type Props = {
  isSelected?: boolean;
  children: React.ReactNode;
};

export const Tab: React.FC<Props> = ({ isSelected, children }) => {
  return <StyledTab isSelected={isSelected}>{children}</StyledTab>;
};

const StyledTab = styled.div<{ isSelected?: boolean }>`
  font-size: 22px;
  font-weight: bold;
  color: #9a9a9a;
  position: relative;
  top: 3px;
  padding: 10px 6px;
  ${({ isSelected }) => isSelected && `color: #1b1b1b;`};
  ${({ isSelected }) => isSelected && `border-bottom: 4px solid #484848;`};
`;
