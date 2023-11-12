import styled from '@emotion/styled';
import { RegionCard } from './RegionCard';

export const ShowContainer: React.FC = () => {
  return (
    <StyledShowContainer>
      <RegionCard />
    </StyledShowContainer>
  );
};

const StyledShowContainer = styled.div`
  max-width: 1200px;
  margin: 0 auto;
`;
