import styled from '@emotion/styled';
import SearchIcon from '@mui/icons-material/Search';
import { IconButton, InputBase, Paper } from '@mui/material';
import { useEffect, useRef, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { getSearchSuggestions } from '~/apis/venue';
import { SearchSuggestion } from '~/types/api.types';
import { ResultBox } from './ResultBox';

export const SearchBox: React.FC = () => {
  const navigate = useNavigate();
  const [searchText, setSearchText] = useState('');
  const [searchSuggestions, setSearchSuggestions] = useState<
    SearchSuggestion[]
  >([]);
  const inputRef = useRef<HTMLInputElement>(null);

  const onSearchTextSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    if (searchText.trim().length > 0) {
      navigate(`/map?word=${searchText}`);
    }
  };

  useEffect(() => {
    if (searchText.trim().length === 0) {
      setSearchSuggestions([]);
      return;
    }

    const timer = setTimeout(async () => {
      const suggestions = await getSearchSuggestions(searchText);
      setSearchSuggestions(suggestions);
    }, 120);

    return () => {
      clearTimeout(timer);
    };
  }, [searchText]);

  return (
    <StyledSearchBox>
      <Paper
        component="form"
        onSubmit={onSearchTextSubmit}
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
          autoComplete="off"
          value={searchText}
          onChange={(e) => setSearchText(e.target.value)}
          ref={inputRef}
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
