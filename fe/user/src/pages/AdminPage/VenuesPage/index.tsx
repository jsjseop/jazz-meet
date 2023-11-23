import styled from '@emotion/styled';
import Paper from '@mui/material/Paper';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import { useEffect, useState } from 'react';
import { getVenueList } from '~/apis/venue';
import { PaginationBox } from '~/components/PaginationBox';
import { SearchParams, VenueList } from '~/types/api.types';

export const VenuesPage: React.FC = () => {
  const [venueList, setVenueList] = useState<VenueList>();
  const [getVenueListParams, setGetVenueListParams] = useState<SearchParams>({
    page: 1,
  });

  useEffect(() => {
    (async () => {
      const venueList = await getVenueList(getVenueListParams);

      setVenueList(venueList);
    })();
  }, [getVenueListParams]);

  const onPageChange = (_: React.ChangeEvent<unknown>, page: number) => {
    setGetVenueListParams((prev) => ({ ...prev, page }));
  };

  return (
    <StyledVenuesPage>
      <StyledHeader>공연장 목록</StyledHeader>

      {venueList ? (
        <>
          <TableContainer component={Paper}>
            <Table>
              <TableHead>
                <TableRow>
                  <TableCell>id</TableCell>
                  <TableCell>공연장명</TableCell>
                  <TableCell>주소</TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {venueList.venues.map((venue) => (
                  <TableRow
                    key={venue.name}
                    sx={{
                      '&:last-child td, &:last-child th': { border: 0 },
                      ':hover': {
                        backgroundColor: '#DBE1E4',
                        cursor: 'pointer',
                      },
                    }}
                  >
                    <TableCell>{venue.id}</TableCell>
                    <TableCell>{venue.name}</TableCell>
                    <TableCell>{venue.address}</TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          </TableContainer>
          <PaginationBox
            maxPage={venueList.maxPage}
            currentPage={venueList.currentPage}
            onChange={onPageChange}
          />
        </>
      ) : (
        <div>로딩중...</div>
      )}
    </StyledVenuesPage>
  );
};

const StyledVenuesPage = styled.div`
  padding: 20px;
  display: flex;
  flex-direction: column;
  gap: 20px;
`;

const StyledHeader = styled.div`
  font-size: 24px;
`;
