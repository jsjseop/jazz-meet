import styled from '@emotion/styled';
import { VenueList } from './VenueList';
import { Outlet } from 'react-router-dom';
import { VenueListData } from '~/hooks/useVenueList';

type Props = {
  mapRef: React.RefObject<HTMLDivElement>;
} & VenueListData;

export const Panel: React.FC<Props> = ({ mapRef, ...venueListData }) => {
  return (
    <StyledPanel>
      <VenueList {...venueListData} />
      <Outlet context={mapRef} />
    </StyledPanel>
  );
};

const StyledPanel = styled.div`
  position: relative;
  width: 100%;
  height: 100%;
  display: flex;
`;
