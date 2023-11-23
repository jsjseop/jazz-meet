import styled from '@emotion/styled';
import Paper from '@mui/material/Paper';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import { useEffect, useState } from 'react';
import { getInquiryData } from '~/apis/inquiry';
import { PaginationBox } from '~/components/PaginationBox';
import { INQUIRY_CATEGORIES } from '~/constants/INQUIRY';
import { clickableStyle } from '~/styles/designSystem';
import { InquiryData, InquiryParams } from '~/types/api.types';
import { InquiryCategories } from '~/types/inquiry.types';

export const InquiriesPage: React.FC = () => {
  const [inquiryList, setInquiryList] = useState<InquiryData>();
  const [inquiryListParams, setInquiryListParams] = useState<InquiryParams>({
    category: '서비스',
    page: 1,
  });

  useEffect(() => {
    (async () => {
      const inquiryList = await getInquiryData(inquiryListParams);

      setInquiryList(inquiryList);
    })();
  }, [inquiryListParams]);

  const selectCategory = (category: InquiryCategories) => {
    setInquiryListParams((prev) => ({ ...prev, category, page: 1 }));
  };

  const onPageChange = (_: React.ChangeEvent<unknown>, page: number) => {
    setInquiryListParams((prev) => ({ ...prev, page }));
  };

  return (
    <StyledVenuesPage>
      <StyledHeader>문의</StyledHeader>

      <StyledCategories>
        {INQUIRY_CATEGORIES.map((category, index) => (
          <StyledCategory key={index} onClick={() => selectCategory(category)}>
            {category}
          </StyledCategory>
        ))}
      </StyledCategories>

      {inquiryList ? (
        <>
          <TableContainer component={Paper}>
            <Table>
              <TableHead>
                <TableRow>
                  <TableCell>id</TableCell>
                  <TableCell align="center">상태</TableCell>
                  <TableCell>내용</TableCell>
                  <TableCell align="center">닉네임</TableCell>
                  <TableCell align="center">생성일시</TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {inquiryList.inquiries.map((inquiry) => (
                  <TableRow
                    key={inquiry.id}
                    sx={{
                      '&:last-child td, &:last-child th': { border: 0 },
                      ':hover': {
                        backgroundColor: '#DBE1E4',
                        cursor: 'pointer',
                      },
                    }}
                  >
                    <TableCell>{inquiry.id}</TableCell>
                    <TableCell align="center">{inquiry.status}</TableCell>
                    <TableCell>{inquiry.content}</TableCell>
                    <TableCell align="center">{inquiry.nickname}</TableCell>
                    <TableCell align="center">
                      {new Date(inquiry.createdAt).toLocaleString()}
                    </TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          </TableContainer>
          <PaginationBox
            maxPage={inquiryList.maxPage}
            currentPage={inquiryList.currentPage}
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

const StyledCategories = styled.div`
  display: flex;
  gap: 20px;
`;

const StyledCategory = styled.div`
  ${clickableStyle};
  cursor: pointer;
`;

const StyledHeader = styled.div`
  font-size: 24px;
`;
