import styled from '@emotion/styled';
import SearchIcon from '@mui/icons-material/Search';
import { IconButton, InputBase, Paper } from '@mui/material';
import { useEffect, useRef, useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import { getSearchSuggestions } from '~/apis/venue';
import { SearchSuggestion } from '~/types/api.types';
import { SuggestionBox } from './SuggestionBox';

export const SearchBox: React.FC = () => {
  const navigate = useNavigate();
  const { search: queryString } = useLocation();
  const [searchText, setSearchText] = useState('');
  const [searchSuggestions, setSearchSuggestions] = useState<
    SearchSuggestion[]
  >([]);
  const [isSuggestionBoxOpen, setIsSuggestionBoxOpen] = useState(false);
  const [activeSuggestionIndex, setActiveSuggestionIndex] =
    useState<number>(-1);
  const searchBoxRef = useRef<HTMLDivElement>(null);

  const query = new URLSearchParams(queryString);
  const word = query.get('word');
  const isSearchTextEmpty = searchText.trim().length === 0;

  const showSuggestionBox = () => setIsSuggestionBoxOpen(true);
  const hideSuggestionBox = () => setIsSuggestionBoxOpen(false);

  const onInputBaseFocus = () => {
    if (!isSearchTextEmpty) {
      showSuggestionBox();
    }
  };

  const onSearchTextSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    if (activeSuggestionIndex !== -1) {
      const { id } = searchSuggestions[activeSuggestionIndex];
      navigate(`/map?venueId=${id}`);
      hideSuggestionBox();
      return;
    }

    if (!isSearchTextEmpty) {
      navigate(`/map?word=${searchText}`);
      hideSuggestionBox();
    }
  };

  const changeActiveSuggestion = (e: React.KeyboardEvent<HTMLInputElement>) => {
    if (e.key === 'ArrowDown') {
      e.preventDefault();
      setActiveSuggestionIndex((asi) =>
        asi === searchSuggestions.length - 1 ? -1 : asi + 1,
      );
      return;
    }

    if (e.key === 'ArrowUp') {
      e.preventDefault();
      setActiveSuggestionIndex((asi) =>
        asi === -1 ? searchSuggestions.length - 1 : asi - 1,
      );
    }
  };

  useEffect(() => {
    if (isSearchTextEmpty) {
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
  }, [searchText, isSearchTextEmpty]);

  useEffect(() => {
    setIsSuggestionBoxOpen(searchSuggestions.length > 0);
  }, [searchSuggestions]);

  useEffect(() => {
    if (isSuggestionBoxOpen === false) {
      setActiveSuggestionIndex(-1);
    }
  }, [isSuggestionBoxOpen]);

  return (
    <StyledSearchBox id="search-box" ref={searchBoxRef}>
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
          value={searchText || word || ''}
          onChange={(e) => setSearchText(e.target.value)}
          onKeyDown={changeActiveSuggestion}
          onFocus={onInputBaseFocus}
        />
        <IconButton type="button" sx={{ p: '10px' }} aria-label="search">
          <SearchIcon />
        </IconButton>
      </Paper>

      <SuggestionBox
        suggestions={searchSuggestions}
        open={isSuggestionBoxOpen}
        searchBoxRef={searchBoxRef}
        activeIndex={activeSuggestionIndex}
        onClose={hideSuggestionBox}
      />
    </StyledSearchBox>
  );
};

const StyledSearchBox = styled.div`
  position: relative;
  z-index: 2;
`;
