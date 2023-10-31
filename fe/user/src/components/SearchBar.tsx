import SearchIcon from '@mui/icons-material/Search';
import { IconButton, InputBase, Paper } from '@mui/material';
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';

export const SearchBar: React.FC = () => {
  const navigate = useNavigate();
  const [keyword, setKeyword] = useState('');

  const onKeywordChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setKeyword(e.target.value);
  };

  const onKeyDown = (e: React.KeyboardEvent<HTMLInputElement>) => {
    if (e.key === 'Enter') {
      e.preventDefault();
      searchVenueByKeyword();
    }
  };

  const searchVenueByKeyword = () => {
    if (keyword.trim().length === 0) {
      setKeyword('');

      // TODO: (토스트 팝업)빈 검색어로는 검색할 수 없습니다.
      return;
    }

    navigate(`/map?word=${keyword}`);
  };

  return (
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
        value={keyword}
        onChange={onKeywordChange}
        onKeyDown={onKeyDown}
      />
      <IconButton
        type="button"
        sx={{ p: '10px' }}
        aria-label="search"
        onClick={() => searchVenueByKeyword()}
      >
        <SearchIcon />
      </IconButton>
    </Paper>
  );
};
