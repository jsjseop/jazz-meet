import styled from '@emotion/styled';
import { Outlet } from 'react-router-dom';
import { SearchedVenues } from '~/types/api.types';
import { VenueList } from './VenueList';

type Props = {
  mapElement: React.RefObject<HTMLDivElement>;
  searchedVenus?: SearchedVenues;
  changeVenueListPage: (page: number) => void;
};

export const Panel: React.FC<Props> = ({
  mapElement,
  searchedVenus,
  changeVenueListPage,
}) => {
  return (
    <StyledPanel>
      <VenueList
        searchedVenus={searchedVenus}
        changeVenueListPage={changeVenueListPage}
      />
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
