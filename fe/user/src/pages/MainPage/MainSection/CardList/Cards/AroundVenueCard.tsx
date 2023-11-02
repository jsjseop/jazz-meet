import styled from '@emotion/styled';
import { useNavigate } from 'react-router-dom';
import { AroundVenue } from '~/types/api.types';

type Props = {
  aroundVenue: AroundVenue;
};

export const AroundVenueCard: React.FC<Props> = ({ aroundVenue }) => {
  const { id, thumbnailUrl, name, address, latitude, longitude } = aroundVenue;
  const navigate = useNavigate();

  return (
    <StyledCard
      onClick={() =>
        navigate(`/map/venues/${id}`, { state: { latitude, longitude } })
      }
    >
      <StyledCardImage src={thumbnailUrl} alt="around-venue" />
      <StyledTitleContainer>
        <StyledTitle>{name}</StyledTitle>
        <StyledSubTitle>{address}</StyledSubTitle>
      </StyledTitleContainer>
    </StyledCard>
  );
};

const StyledCard = styled.div`
  width: 100%;
  display: flex;
  flex-direction: column;
  gap: 24px;
  cursor: pointer;
`;

const StyledCardImage = styled.img`
  width: 100%;
  height: 380px;
  object-fit: cover;
`;

const StyledTitleContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 2px;
`;

const StyledTitle = styled.div`
  font-size: 22px;
  font-weight: bold;
  line-height: 140%;
  color: #141313;
`;

const StyledSubTitle = styled.div`
  font-size: 22px;
  font-weight: medium;
  line-height: 140%;
  letter-spacing: -1px;
  color: #686970;
`;
