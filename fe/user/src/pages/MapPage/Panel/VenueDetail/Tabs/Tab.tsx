import styled from '@emotion/styled';

type Props = {
  isSelected?: boolean;
  children: React.ReactNode;
};

export const Tab: React.FC<Props> = ({ isSelected, children }) => {
  return <StyledTab isSelected={isSelected}>{children}</StyledTab>;
};

const StyledTab = styled.div<{ isSelected?: boolean }>`
  position: relative;
  top: 2.5px;
  padding: 6px;
  ${({ isSelected }) => isSelected && `border-bottom: 4px solid #484848;`};
`;
