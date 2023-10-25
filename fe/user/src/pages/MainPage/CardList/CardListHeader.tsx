import styled from '@emotion/styled';

type Props = {
  title: string;
  onMoreClick?: () => void;
};

export const CardListHeader: React.FC<Props> = ({ title, onMoreClick }) => {
  return (
    <StyledCardListHeader>
      <LeftContainer>
        <Title>{title}</Title>
      </LeftContainer>

      {onMoreClick && <MoreButton>더보기</MoreButton>}
    </StyledCardListHeader>
  );
};

const StyledCardListHeader = styled.div`
  margin-bottom: 31px;
  display: flex;
  justify-content: space-between;
`;

const LeftContainer = styled.div`
  display: flex;
  align-items: center;
`;

const Title = styled.div`
  font-size: 24px;
  color: #ff4d00;
`;

const MoreButton = styled.button`
  cursor: pointer;
`;
