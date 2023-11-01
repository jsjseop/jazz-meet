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
      <CardImage src={thumbnailUrl} alt="around-venue" />
      <Title>{name}</Title>
      <SubTitle>{address}</SubTitle>
    </StyledCard>
  );
};

const StyledCard = styled.div`
  width: 100%;
  box-sizing: border-box;
  margin-bottom: 37px;
`;

const CardImage = styled.img`
  width: 100%;
  height: 380px;
  object-fit: cover;
  margin-bottom: 24px;
`;

const Title = styled.div`
  margin-bottom: 5px;
`;

const SubTitle = styled.div``;
