import styled from '@emotion/styled';
import SearchIcon from '@mui/icons-material/Search';
import { Link } from 'react-router-dom';
import JazzMeet from '~/assets/icons/JazzMeet.svg?react';

export const Header: React.FC = () => {
  return (
    <StyledHeader>
      <div></div>
      <Link to="/">
        <JazzMeet fill="#ff4d00" />
      </Link>

      {/* <SearchBox /> */}
      <StyledSearchButton>
        <SearchIcon />
      </StyledSearchButton>
    </StyledHeader>
  );
};

const StyledHeader = styled.header`
  width: 100%;
  height: 60px;
  box-sizing: border-box;
  background-color: #000;
  display: flex;
  justify-content: space-between;
  align-items: center;

  > div:first-of-type {
    width: 56px;
  }

  a > svg {
    width: 140px;
    height: auto;
  }
`;

const StyledSearchButton = styled.button`
  width: 56px;
  height: 100%;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;

  svg {
    fill: #fff;
  }
`;
