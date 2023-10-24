import styled from '@emotion/styled';
import SearchIcon from '@mui/icons-material/Search';

export const SearchBar: React.FC = () => {
  return (
    <StyledSearchBar>
      <StyledInput placeholder="함께 맞는 주말 햇살, 나란히 듣는 재즈" />
      <SearchIcon />
    </StyledSearchBar>
  );
};

const StyledSearchBar = styled.div`
  border: 1px solid black;
`;

const StyledInput = styled.input`
  border: 0;
`;
