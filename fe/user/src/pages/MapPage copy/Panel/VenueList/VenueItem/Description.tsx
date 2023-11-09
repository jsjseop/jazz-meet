import styled from '@emotion/styled';
import { VenueData } from '~/types/api.types';
import { isoToTimeFormat } from '~/utils/formatTime';

type Props = Omit<VenueData, 'id' | 'thumbnailUrl' | 'latitude' | 'longitude'>;

export const Description: React.FC<Props> = ({
  name,
  address,
  description,
  showInfo,
}) => {
  return (
    <StyledDescription>
      <StyledHeader>
        <StyledName>{name}</StyledName>
        <StyledAddress>{address}</StyledAddress>
      </StyledHeader>
      <StyledMainContent>
        <StyledIntroduction>{description}</StyledIntroduction>
        <StyledSchedule>
          {showInfo.map(({ startTime, endTime }, index) => (
            <StyledShowTime key={index}>
              <span>{index + 1}부</span>
              <span>
                {isoToTimeFormat(startTime)}~{isoToTimeFormat(endTime)}
              </span>
            </StyledShowTime>
          ))}
        </StyledSchedule>
      </StyledMainContent>
    </StyledDescription>
  );
};

const StyledDescription = styled.article`
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 75px;
`;

const StyledHeader = styled.header`
  display: flex;
  flex-direction: column;
  gap: 8px;
`;

const StyledName = styled.h3`
  font-size: 24px;
  font-weight: 800;
  color: #1b1b1b;
`;

const StyledAddress = styled.span`
  font-size: 20px;
  font-weight: 500;
  color: #848484;
`;

const StyledMainContent = styled.section`
  display: flex;
  flex-direction: column;
  gap: 11px;
`;

const StyledIntroduction = styled.p`
  font-size: 20px;
  font-weight: 500;
  color: #5b5b5b;
  line-height: 1.3;
  letter-spacing: -4%;
  white-space: pre-line;
  display: -webkit-box;
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;
  overflow: hidden;
`;

const StyledSchedule = styled.div`
  & * {
    font-size: 18px;
    font-weight: 400;
    color: #c6c6c6;
  }
`;

const StyledShowTime = styled.div`
  display: flex;
  gap: 8px;
`;
