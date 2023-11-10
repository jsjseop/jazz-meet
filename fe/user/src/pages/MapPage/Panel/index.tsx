import styled from '@emotion/styled';
import { Outlet } from 'react-router-dom';
import { SearchedVenues } from '~/types/api.types';
import { VenueList } from './VenueList';

type Props = {
  mapElement: React.RefObject<HTMLDivElement>;
  searchedVenus?: SearchedVenues;
  handleChangeVenueListPage: (page: number) => void;
};

export const Panel: React.FC<Props> = ({
  mapElement,
  searchedVenus,
  handleChangeVenueListPage,
}) => {
  return (
    <StyledPanel>
      <VenueList
        searchedVenus={searchedVenus}
        handleChangeVenueListPage={handleChangeVenueListPage}
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
