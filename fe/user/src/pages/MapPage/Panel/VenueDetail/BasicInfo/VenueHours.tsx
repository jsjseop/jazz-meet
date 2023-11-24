import styled from '@emotion/styled';
import ExpandMoreOutlinedIcon from '@mui/icons-material/ExpandMoreOutlined';
import { VenueHour } from '~/types/api.types';

type Props = {
  venueHours: VenueHour[];
};

export const VenueHours: React.FC<Props> = ({ venueHours }) => {
  return (
    <StyledVenueHours>
      <StyledTodayBusinessHours>
        영업시간
        <ExpandMoreOutlinedIcon />
      </StyledTodayBusinessHours>

      {venueHours.map((venueHour, index) => (
        <StyledBasicInfoText key={index}>
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
`;

const StyledBasicInfoText = styled.div`
  height: 32px;
  display: flex;
  align-items: center;
`;
