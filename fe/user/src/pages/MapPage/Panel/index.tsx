import styled from '@emotion/styled';
import { Outlet } from 'react-router-dom';
import { SearchedVenues } from '~/types/api.types';
import { RenderType } from '~/types/device.types';
import { VenueList } from './VenueList';

type Props = {
  mapElement: React.RefObject<HTMLDivElement>;
  searchedVenus?: SearchedVenues;
  renderType: RenderType;
  handleChangeVenueListPage: (page: number) => void;
};

export const Panel: React.FC<Props> = ({
  mapElement,
  searchedVenus,
  renderType,
  handleChangeVenueListPage,
}) => {
  return (
    <StyledPanel renderType={renderType}>
      <VenueList
        searchedVenus={searchedVenus}
        handleChangeVenueListPage={handleChangeVenueListPage}
      />
      <Outlet context={mapElement} />
    </StyledPanel>
  );
};

const StyledPanel = styled.div<{ renderType: RenderType }>`
  position: relative;
  width: 100%;
  height: 100%;
  display: flex;
  ${({ renderType }) =>
    renderType === 'all' || renderType === 'list' ? '' : 'display: none;'};
`;
