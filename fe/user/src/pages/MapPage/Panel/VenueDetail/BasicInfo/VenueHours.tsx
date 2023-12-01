import styled from '@emotion/styled';
import ExpandMoreOutlinedIcon from '@mui/icons-material/ExpandMoreOutlined';
import { VenueHour } from '~/types/api.types';
import { getKoreanWeekdayName } from '~/utils/dateUtils';

type Props = {
  venueHours: VenueHour[];
};

export const VenueHours: React.FC<Props> = ({ venueHours }) => {
  const today = getKoreanWeekdayName(new Date().getDay()) + '요일';
  const todayBusinessHours = venueHours.find(
    (venueHour) => venueHour.day === today,
  )?.businessHours;

  return (
    <StyledVenueHours>
      <StyledTodayBusinessHours>
        {todayBusinessHours || '오늘의 영업시간 정보가 없습니다.'}
        <ExpandMoreOutlinedIcon />
      </StyledTodayBusinessHours>

      {venueHours.map((venueHour, index) => (
        <StyledBasicInfoText key={index} $highlight={today === venueHour.day}>
          {`${venueHour.day} | ${venueHour.businessHours}`}
        </StyledBasicInfoText>
      ))}
    </StyledVenueHours>
  );
};

const StyledVenueHours = styled.details`
  &[open] summary > svg {
    transform: rotate(180deg);
  }
`;

const StyledTodayBusinessHours = styled.summary`
  height: 32px;
  display: flex;
  align-items: center;

  &::marker {
    display: none;
  }

  &:hover {
    cursor: pointer;
  }
`;

const StyledBasicInfoText = styled.div<{ $highlight: boolean }>`
  height: 32px;
  display: flex;
  align-items: center;
  font-weight: ${({ $highlight }) => ($highlight ? 'bold' : 'normal')};
`;
