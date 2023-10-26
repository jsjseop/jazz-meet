import { PaginationBox } from '@components/PaginationBox';
import styled from '@emotion/styled';
import { useMemo, useState } from 'react';
import { Header } from './Header';
import { VenueItem } from './VenueItem';

export const VenueList: React.FC = () => {
  const maxPage = useMemo(() => 25, []);
  const [pageNumber, setPageNumber] = useState(
    Math.floor(Math.random() * maxPage),
  );

  const handlePageChange = (
    event: React.ChangeEvent<unknown>,
    value: number,
  ) => {
    setPageNumber(value);
  };

  return (
    <StyledVenueList>
      <ContentContainer>
        <Header />
        <Venues>
          <VenueItem />
          <VenueItem />
          <VenueItem />
          <VenueItem />
          <VenueItem />
        </Venues>
      </ContentContainer>
      <PaginationBox
        maxPage={maxPage}
        currentPage={pageNumber}
        onChange={handlePageChange}
      />
    </StyledVenueList>
  );
};

const StyledVenueList = styled.div`
  width: 100%;
  height: inherit;
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

const ContentContainer = styled.div`
  padding: 20px;
`;

const Venues = styled.ul`
  display: flex;
  flex-direction: column;
  gap: 20px;
  margin-top: 20px;
`;
