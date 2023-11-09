import styled from '@emotion/styled';
import { Outlet } from 'react-router-dom';

type Props = {
  mapElement: React.RefObject<HTMLDivElement>;
};

export const Panel: React.FC<Props> = ({ mapElement }) => {
  return (
    <StyledPanel>
      {/* <VenueList {...venueListData} /> */}
      <Outlet context={mapElement} />
    </StyledPanel>
  );
};

const StyledPanel = styled.div`
  position: relative;
  width: 100%;
  height: 100%;
  display: flex;
`;
