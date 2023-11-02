import styled from '@emotion/styled';

type Props = {
  title: string;
  onMoreClick?: () => void;
};

export const CardListHeader: React.FC<Props> = ({ title, onMoreClick }) => {
  return (
    <StyledCardListHeader>
      <StyledLeftContainer>
        <StyledTitle>{title}</StyledTitle>
      </StyledLeftContainer>

      {onMoreClick && <StyledMoreButton>더보기</StyledMoreButton>}
    </StyledCardListHeader>
  );
};

const StyledCardListHeader = styled.div`
  margin-bottom: 31px;
  display: flex;
  justify-content: space-between;
`;

const StyledLeftContainer = styled.div`
  display: flex;
  align-items: center;
`;

const StyledTitle = styled.div`
  font-size: 24px;
  font-weight: bold;
  letter-spacing: -4%;
  color: #ff4d00;
`;

const StyledMoreButton = styled.button`
  cursor: pointer;
`;
