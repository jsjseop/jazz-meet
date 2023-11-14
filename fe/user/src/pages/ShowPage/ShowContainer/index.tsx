import styled from '@emotion/styled';
import { useShowStore } from '~/stores/useShowStore';
import { RegionCard } from './RegionCard';

export const ShowContainer: React.FC = () => {
  const { showsAtDate } = useShowStore((state) => ({
    showsAtDate: state.showsAtDate,
  }));

  return (
    <StyledShowContainer>
      {showsAtDate.length > 0 ? (
        showsAtDate.map((showRegion) => (
          <RegionCard key={showRegion.region} {...showRegion} />
        ))
      ) : (
        <StyledShowEmpty>이번 달에는 공연이 없네요 ㅠㅠ</StyledShowEmpty>
      )}
    </StyledShowContainer>
  );
};

const StyledShowContainer = styled.div`
  width: 100%;
  margin: 25px auto;
`;

const StyledShowEmpty = styled.div`
  width: 100%;
  margin: 25px auto;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 32px;
  font-weight: bold;
  color: #a3a4a9;
`;
