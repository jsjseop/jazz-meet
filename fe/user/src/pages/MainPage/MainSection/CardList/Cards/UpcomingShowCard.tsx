import styled from '@emotion/styled';
import { useNavigate } from 'react-router-dom';
import { UpcomingShow } from 'types/api.types';

type Props = {
  upcomingShow: UpcomingShow;
};

export const UpcomingShowCard: React.FC<Props> = ({ upcomingShow }) => {
  const { venueId, showId, posterUrl, showName, startTime, endTime } =
    upcomingShow;
  const navigate = useNavigate();

  return (
    <StyledCard onClick={() => navigate(`/venues/${venueId}/shows/${showId}`)}>
      <CardImage src={posterUrl} alt="poster" />
      <Title>{showName}</Title>
      <SubTitle>{startTime}</SubTitle>
      <SubTitle>{endTime}</SubTitle>
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
