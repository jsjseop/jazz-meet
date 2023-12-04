import styled from '@emotion/styled';

type Props = {
  children: React.ReactNode;
};

export const CardList: React.FC<Props> = ({ children }) => {
  return <StyledCardList>{children}</StyledCardList>;
};

const StyledCardList = styled.div`
  padding: 0 60px;
`;
