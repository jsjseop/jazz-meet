import styled from '@emotion/styled';
import { VenueItemData } from '~/types/api.types';
import { Description } from './Description';
import { Thumbnail } from './Thumbnail';

type Props = VenueItemData;

export const VenueItem: React.FC<Props> = (venueData) => {
  return (
    <StyledVenue>
      <Description {...venueData} />
      <Thumbnail url={venueData.thumbnailUrl} />
    </StyledVenue>
  );
};

const StyledVenue = styled.div`
  padding: 20px;
  display: flex;
  gap: 40px;
  border: 1px solid #dbdbdb;
  border-radius: 8.78px;
  transition: box-shadow 0.2s ease-in-out;

  &:hover {
    box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.3);
  }

  &:active {
    box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.1);
  }
`;
