import styled from '@emotion/styled';

type Props = {
  children: React.ReactNode;
};

export const Tabs: React.FC<Props> = ({ children }) => {
  return <StyledTabs>{children}</StyledTabs>;
};

const StyledTabs = styled.div`
  position: relative;
  padding-left: 54px;
  border-bottom: 1px solid #c7c7c7;
  display: flex;
  gap: 12px;
`;
