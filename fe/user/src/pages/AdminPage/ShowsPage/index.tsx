import styled from '@emotion/styled';
import Paper from '@mui/material/Paper';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import { useEffect, useState } from 'react';
import { getShowList } from '~/apis/show';
import { PaginationBox } from '~/components/PaginationBox';
import { SearchParams, ShowList } from '~/types/api.types';

export const ShowsPage: React.FC = () => {
  const [showList, setShowList] = useState<ShowList>();
  const [getShowListParams, setGetShowListParams] = useState<SearchParams>({
    page: 1,
  });

  useEffect(() => {
    (async () => {
      const showList = await getShowList(getShowListParams);

      setShowList(showList);
    })();
  }, [getShowListParams]);

  const onPageChange = (_: React.ChangeEvent<unknown>, page: number) => {
    setGetShowListParams((prev) => ({ ...prev, page }));
  };

  return (
    <StyledVenuesPage>
      <StyledHeader>공연 목록</StyledHeader>

      {showList ? (
        <>
          <TableContainer component={Paper}>
            <Table>
              <TableHead>
                <TableRow>
                  <TableCell>id</TableCell>
                  <TableCell>팀명</TableCell>
                  <TableCell align="center">시작시간</TableCell>
                  <TableCell align="center">종료시간</TableCell>
                  <TableCell align="center">공연장명</TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {showList.shows.map((venue) => (
                  <TableRow
                    key={venue.id}
                    sx={{
                      '&:last-child td, &:last-child th': { border: 0 },
                      ':hover': {
                        backgroundColor: '#DBE1E4',
                        cursor: 'pointer',
                      },
                    }}
                  >
                    <TableCell>{venue.id}</TableCell>
                    <TableCell>{venue.teamName}</TableCell>
                    <TableCell align="center">
                      {venue.startTime.replace('T', ' ')}
                    </TableCell>
                    <TableCell align="center">
                      {venue.endTime.replace('T', ' ')}
                    </TableCell>
                    <TableCell align="center">{venue.venueName}</TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          </TableContainer>
          <PaginationBox
            maxPage={showList.maxPage}
            currentPage={showList.currentPage}
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
