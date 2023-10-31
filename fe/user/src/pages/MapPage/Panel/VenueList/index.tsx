import { PaginationBox } from '@components/PaginationBox';
import styled from '@emotion/styled';
import ErrorOutlineIcon from '@mui/icons-material/ErrorOutline';
import { Link } from 'react-router-dom';
import { VenueData } from 'types/api.types';
import { VenueItem } from './VenueItem';

export type VenueListProps = {
  venueList: VenueData[];
  venueCount: number;
  currentPage: number;
  maxPage: number;
  updateVenueList: (page: number) => void;
};

export const VenueList: React.FC<VenueListProps> = ({
  venueList,
  venueCount,
  currentPage,
  maxPage,
  updateVenueList,
}) => {
  const handlePageChange = (_: React.ChangeEvent<unknown>, value: number) => {
    updateVenueList(value);
  };

  return (
    <StyledVenueList>
      <StyledTotalCount>
        <h2>전체</h2>
        <span>{venueCount}</span>
      </StyledTotalCount>

      {venueList.length > 0 ? (
        <>
          <StyledVenues>
            {venueList.map((venue) => (
              <Link to={`/map/venues/${venue.id}`} key={venue.id}>
                <VenueItem {...venue} />
              </Link>
            ))}
          </StyledVenues>
          <PaginationBox
            maxPage={maxPage}
            currentPage={currentPage}
            onChange={handlePageChange}
          />
        </>
      ) : (
        <EmptyList>
          <ErrorOutlineIcon />
          검색 결과가 없습니다.
        </EmptyList>
      )}
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

const StyledTotalCount = styled.div`
  display: flex;
  gap: 13px;
  font-size: 26px;
  font-weight: 800;

  & h2 {
    color: #212121;
  }

  & span {
    color: #ff4d00;
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

const EmptyList = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 4px;
  height: 100px;
  font-size: 16px;
  color: #888;
  margin-top: 20px;
`;
