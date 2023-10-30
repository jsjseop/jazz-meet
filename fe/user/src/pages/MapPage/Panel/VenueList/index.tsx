import { PaginationBox } from '@components/PaginationBox';
import styled from '@emotion/styled';
import { VenueData } from 'apis/venue/types';
import { Header } from './Header';
import { VenueItem } from './VenueItem';

export type VenueListProps = {
  venueList: VenueData[];
  venueCount: number;
  currentPage: number;
  maxPage: number;
};

export const VenueList: React.FC<VenueListProps> = ({
  venueList,
  venueCount,
  currentPage,
  maxPage,
}) => {
  const handlePageChange = (_: React.ChangeEvent<unknown>, value: number) => {
    console.log('page', value);
  };

  return (
    <StyledVenueList>
      <Header count={venueCount} />
      <StyledVenues>
        {/* <Link to="/map/venues/1">
          <VenueItem />
        </Link> */}
        {venueList.map((venue) => (
          <VenueItem key={venue.id} {...venue} />
        ))}
      </StyledVenues>
      <PaginationBox
        maxPage={maxPage}
        currentPage={currentPage}
        onChange={handlePageChange}
      />
    </StyledVenueList>
  );
};

const StyledVenueList = styled.div`
  width: 100%;
  background-color: #fff;
  padding: 20px;
  overflow-y: auto;

  &::-webkit-scrollbar {
    width: 18px;
  }

  &::-webkit-scrollbar-thumb {
    background-clip: padding-box;
    background-color: #c1c1c1;
    border-radius: 10px;
    border: 4px solid transparent;
  }

  &::-webkit-scrollbar-track {
    background-color: transparent;
  }
`;

const StyledVenues = styled.ul`
  display: flex;
  flex-direction: column;
  gap: 20px;
  margin-top: 20px;

  a {
    text-decoration: none;
  }
`;
