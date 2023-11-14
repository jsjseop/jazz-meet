import styled from '@emotion/styled';
import SearchIcon from '@mui/icons-material/Search';
import { IconButton, InputBase, Paper, Typography } from '@mui/material';

type Props = {
  onWordChange: (word: string) => void;
};

export const Header: React.FC<Props> = ({ onWordChange }) => {
  const onSearchSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    const word = new FormData(e.currentTarget)
      .get(SEARCH_INPUT_NAME)
      ?.toString();

    if (!word) {
      return;
    }

    onWordChange(word);
  };

  return (
    <StyledHeader>
      <Typography variant="h4" sx={{ fontWeight: 'bold' }}>
        문의사항
      </Typography>
      <Paper
        component="form"
        onSubmit={onSearchSubmit}
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
          name={SEARCH_INPUT_NAME}
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

const SEARCH_INPUT_NAME = 'word';

const StyledHeader = styled.header`
  width: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin: 47px 0;
`;
