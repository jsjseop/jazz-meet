import styled from '@emotion/styled';
import { VenueList, VenueListProps } from './VenueList';
import { Outlet } from 'react-router-dom';

type Props = {
  mapRef: React.RefObject<HTMLDivElement>;
} & VenueListProps;

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
