import styled from '@emotion/styled';
import { useShowStore } from '~/stores/useShowStore';
import { RegionCard } from './RegionCard';

export const ShowContainer: React.FC = () => {
  const { showsAtDate } = useShowStore((state) => ({
    showsAtDate: state.showsAtDate,
  }));

  return (
    <StyledShowContainer>
      {showsAtDate.map((showRegion) => (
        <RegionCard key={showRegion.region} {...showRegion} />
      ))}
    </StyledShowContainer>
  );
};

const StyledShowContainer = styled.div`
  width: 100%;
  margin: 25px auto;
`;
