import styled from '@emotion/styled';
import { useNavigate } from 'react-router-dom';
import { useShowDetailStore } from '~/stores/useShowDetailStore';
import { UpcomingShow } from '~/types/api.types';
import { getFormattedDate } from '~/utils/dateUtils';

type Props = {
  upcomingShow: UpcomingShow;
};

export const UpcomingShowCard: React.FC<Props> = ({ upcomingShow }) => {
  const { venueId, showId, posterUrl, teamName, startTime, endTime } =
    upcomingShow;
  const navigate = useNavigate();
  const { setShowId, setShowDate } = useShowDetailStore();
  const goToShowDetail = () => {
    setShowId(showId);
    setShowDate(getFormattedDate(new Date(startTime)));
    navigate(`map/venues/${venueId}`);
  };

  return (
    <StyledCard onClick={() => goToShowDetail()}>
      <StyledCardImage src={posterUrl} alt="poster" />
      <StyledTitleContainer>
        <StyledTitle>{teamName}</StyledTitle>
        <StyledSubTitle>{`${formatTime(startTime)} ~ ${formatTime(
          endTime,
        )}`}</StyledSubTitle>
      </StyledTitleContainer>
    </StyledCard>
  );
};

const formatTime = (date: string) => {
  return new Date(date).toLocaleTimeString('en-US', {
    hour: '2-digit',
    minute: '2-digit',
  });
};

const StyledCard = styled.div`
  width: 100%;
  box-sizing: border-box;
  margin-bottom: 37px;
`;

const StyledCardImage = styled.img`
  width: 100%;
  height: 380px;
  object-fit: cover;
  margin-bottom: 24px;
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
