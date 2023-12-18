import styled from '@emotion/styled';
import { paintSkeleton } from '~/styles/designSystem';

export const CardListSkeleton: React.FC = () => {
  return (
    <StyledCardListSkeleton>
      <StyledCard>
        <StyledImage />
        <StyledText />
        <StyledText />
      </StyledCard>

      <StyledCard>
        <StyledImage />
        <StyledText />
        <StyledText />
      </StyledCard>

      <StyledCard>
        <StyledImage />
        <StyledText />
        <StyledText />
      </StyledCard>

      <StyledCard>
        <StyledImage />
        <StyledText />
        <StyledText />
      </StyledCard>
    </StyledCardListSkeleton>
  );
};

const StyledCardListSkeleton = styled.div`
  width: 100%;
  height: 500px;
  display: flex;
  gap: 10px;
`;

const StyledCard = styled.div`
  width: 260px;

  display: flex;
  flex-direction: column;
  gap: 10px;
`;

const StyledImage = styled.div`
  width: 100%;
  height: 500px;
  border-radius: 8px;
  ${paintSkeleton};
`;

const StyledText = styled.div`
  width: 100%;
  height: 30px;
  border-radius: 8px;
  ${paintSkeleton};
`;
