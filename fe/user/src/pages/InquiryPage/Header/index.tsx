import styled from '@emotion/styled';
import SearchIcon from '@mui/icons-material/Search';
import { IconButton, InputBase, Paper, Typography } from '@mui/material';

export const InquiryPageHeader: React.FC = () => {
  return (
    <StyledHeader>
      <Typography variant="h4" sx={{ fontWeight: 'bold' }}>
        문의사항
      </Typography>
      <Paper
        component="form"
        elevation={0}
        sx={{
          p: '2px 4px',
          display: 'flex',
          alignItems: 'center',
          width: 490,
          backgroundColor: '#EBEBEB',
          backgroundImage: '#EBEBEB',
        }}
      >
        <InputBase
          sx={{ ml: 1, flex: 1 }}
          placeholder="궁금하신 내용을 검색해 보세요."
        />
        <IconButton type="button" sx={{ p: '10px' }} aria-label="search">
          <SearchIcon />
        </IconButton>
      </Paper>
    </StyledHeader>
  );
};

const StyledHeader = styled.header`
  width: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin: 47px 0;
`;
