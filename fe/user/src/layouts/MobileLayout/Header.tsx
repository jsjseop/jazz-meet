import styled from '@emotion/styled';
import SearchIcon from '@mui/icons-material/Search';
import { useState } from 'react';
import { createPortal } from 'react-dom';
import { Link, useLocation } from 'react-router-dom';
import JazzMeet from '~/assets/icons/JazzMeet.svg?react';
import { SearchBox } from '~/components/SearchBox';
import { SEARCH_BOX_Z_INDEX } from '~/constants/Z_INDEX';

export const Header: React.FC = () => {
  const [showSearchBox, setShowSearchBox] = useState(false);
  const { pathname, search } = useLocation();
  const path = pathname + search;
  const [prevPath, setPrevPath] = useState(() => path);

  if (prevPath !== path) {
    setPrevPath(path);
    setShowSearchBox(false);
  }

  return (
    <StyledHeader>
      <div></div>
      <Link to="/">
        <JazzMeet fill="#ff4d00" />
      </Link>

      <StyledSearchContainer>
        <SearchIcon onClick={() => setShowSearchBox((prev) => !prev)} />
      </StyledSearchContainer>

      {showSearchBox &&
        createPortal(
          <StyledSearchBoxContainer>
            <SearchBox />
          </StyledSearchBoxContainer>,
          document.body,
        )}
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

const StyledSearchContainer = styled.div`
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

const StyledSearchBoxContainer = styled.div`
  width: 90%;
  max-width: 490px;
  z-index: ${SEARCH_BOX_Z_INDEX};
  position: fixed;
  top: 100px;
  left: 50%;
  transform: translateX(-50%);
  border-radius: 8px;
  box-shadow: 0 2px 4px 0 rgb(0 0 0 / 50%);
`;
