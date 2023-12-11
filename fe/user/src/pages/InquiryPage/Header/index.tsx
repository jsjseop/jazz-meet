import styled from '@emotion/styled';
import ClearIcon from '@mui/icons-material/Clear';
import SearchIcon from '@mui/icons-material/Search';
import { IconButton, InputBase, Paper, Typography } from '@mui/material';
import { clickableStyle } from '~/styles/designSystem';

type Props = {
  hasSearchWord: boolean;
  onWordChange: (word: string) => void;
  onSearchClear: () => void;
};

export const Header: React.FC<Props> = ({
  hasSearchWord,
  onWordChange,
  onSearchClear,
}) => {
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

      <StyledSearchContainer>
        {hasSearchWord && (
          <StyledSearchClear onClick={onSearchClear}>
            <ClearIcon fontSize="small" />
            <div>검색 필터 삭제</div>
          </StyledSearchClear>
        )}
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
      </StyledSearchContainer>
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

const StyledSearchContainer = styled.div`
  display: flex;
  gap: 20px;
`;

const StyledSearchClear = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  ${clickableStyle};
`;
