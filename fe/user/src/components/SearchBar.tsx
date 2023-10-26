import SearchIcon from '@mui/icons-material/Search';
import { IconButton, InputBase, Paper } from '@mui/material';

export const SearchBar: React.FC = () => {
  return (
    <>
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
        />
        <IconButton type="button" sx={{ p: '10px' }} aria-label="search">
          <SearchIcon />
        </IconButton>
      </Paper>
    </>
  );
};
