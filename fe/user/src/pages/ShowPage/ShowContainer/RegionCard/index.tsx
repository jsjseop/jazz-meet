import styled from '@emotion/styled';
import { ShowRegion } from '~/types/api.types';
import { VenueCard } from './VenueCard';

export const RegionCard: React.FC<ShowRegion> = ({ region, venues }) => {
  return (
    <StyledRegionCard>
      <StyledCardHeader>{region}</StyledCardHeader>
      <StyledVenueCardList>
        {venues.map(({ id, name, shows }) => (
          <VenueCard key={id} name={name} shows={shows} />
        ))}
      </StyledVenueCardList>
    </StyledRegionCard>
  );
};

const StyledRegionCard = styled.div`
  margin-bottom: 54px;
`;

const StyledCardHeader = styled.h2`
  height: 52px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 4px 4px 0 0;
  background-color: #1b1b1b;
  color: #ffffff;
  font-size: 24px;
  font-weight: bold;
`;

const StyledVenueCardList = styled.div`
  margin-top: 22px;
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 22px;
`;
