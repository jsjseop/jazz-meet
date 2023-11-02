import styled from '@emotion/styled';
import SearchIcon from '@mui/icons-material/Search';
import { IconButton, InputBase, Paper } from '@mui/material';
import { useEffect, useState } from 'react';
import { getSearchSuggestions } from '~/apis/venue';
import { SearchSuggestion } from '~/types/api.types';
import { ResultBox } from './ResultBox';

export const SearchBox: React.FC = () => {
  const [searchText, setSearchText] = useState('');
  const [searchSuggestions, setSearchSuggestions] = useState<
    SearchSuggestion[]
  >([]);

  useEffect(() => {
    let timer: ReturnType<typeof setTimeout>;

    if (searchText) {
      timer = setTimeout(async () => {
        const suggestions = await getSearchSuggestions(searchText);
        setSearchSuggestions(suggestions);
      }, 300);
    }

    return () => {
      clearTimeout(timer);
    };
  }, [searchText]);

  return (
    <StyledSearchBox>
      <Paper
        component="form"
        elevation={0}
        sx={{
          p: '2px 4px',
          display: 'flex',
          alignItems: 'center',
          width: 490,
          backgroundColor: '#FFFFFF',
          backgroundImage: '#FFFFFF',
        }}
      >
        <InputBase
          sx={{ ml: 1, flex: 1 }}
          placeholder="함께 맞는 주말 햇살, 나란히 듣는 재즈."
          name="keyword"
          autoComplete="off"
          value={searchText}
          onChange={(e) => setSearchText(e.target.value)}
        />
        <IconButton type="button" sx={{ p: '10px' }} aria-label="search">
          <SearchIcon />
        </IconButton>
      </Paper>

      {searchSuggestions.length > 0 && (
        <ResultBox suggestions={searchSuggestions} />
      )}
    </StyledSearchBox>
  );
};

const StyledSearchBox = styled.div`
  position: relative;
  z-index: 2;
`;
