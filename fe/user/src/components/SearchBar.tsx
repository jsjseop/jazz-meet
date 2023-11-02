import SearchIcon from '@mui/icons-material/Search';
import { IconButton, InputBase, Paper } from '@mui/material';
import { useNavigate } from 'react-router-dom';

export const SearchBar: React.FC = () => {
  const navigate = useNavigate();

  const onKeywordSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    const keyword = e.currentTarget.keyword.value.trim();

    if (keyword.length > 0) {
      navigate(`/map?word=${keyword}`);
    }
  };

  return (
    <Paper
      component="form"
      onSubmit={onKeywordSubmit}
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
      />
      <IconButton type="submit" sx={{ p: '10px' }} aria-label="search">
        <SearchIcon />
      </IconButton>
    </Paper>
  );
};
