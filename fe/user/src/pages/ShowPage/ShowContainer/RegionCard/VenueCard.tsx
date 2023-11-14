import styled from '@emotion/styled';
import { useNavigate } from 'react-router-dom';
import CaretRight from '~/assets/icons/CaretRight.svg?react';
import { ShowDetail } from '~/types/api.types';

type Props = {
  id: number;
  name: string;
  shows: Omit<ShowDetail, 'description'>[];
};

export const VenueCard: React.FC<Props> = ({ id, name, shows }) => {
  const navigate = useNavigate();

  const navigateToVenueDetail = () => navigate(`/map/venues/${id}`);

  return (
    <StyledVenueCard>
      <StyledCardHeader onClick={navigateToVenueDetail}>
        <span>{name}</span>
        <CaretRight />
      </StyledCardHeader>
      {shows.map((show) => {
        const showTime = `${show.startTime.slice(
          11,
          16,
        )} - ${show.endTime.slice(11, 16)}`;
        return (
          <StyledShowInfo key={show.id}>
            <StyledShowTime>{showTime}</StyledShowTime>
            <StyledShowTeam>{show.teamName}</StyledShowTeam>
          </StyledShowInfo>
        );
      })}
    </StyledVenueCard>
  );
};

const StyledVenueCard = styled.div`
  border-radius: 6px;
  border: 1px solid #dbe1e4;
  padding: 12px 0;
`;

const StyledCardHeader = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 12px 20px;
  font-size: 24px;
  font-weight: bold;
  color: #1b1b1b;

  &:hover {
    cursor: pointer;
    opacity: 0.7;
  }
`;

const StyledShowInfo = styled.div`
  display: flex;
  align-items: center;
  gap: 42px;
  padding: 7px 20px;
  font-size: 20px;
  font-weight: 500;
`;

const StyledShowTime = styled.span`
  white-space: nowrap;
  color: #47484e;
`;

const StyledShowTeam = styled.span`
  color: #686970;
  text-overflow: ellipsis;
  white-space: nowrap;
  overflow: hidden;
`;
