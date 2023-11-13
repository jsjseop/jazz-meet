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
  width: 100%;
  margin: 25px auto;
`;
