import styled from '@emotion/styled';
import { Link, useLocation } from 'react-router-dom';
import JazzMeet from '~/assets/icons/JazzMeet.svg?react';
import { SearchBox } from '~/components/SearchBox';

export const Header: React.FC = () => {
  const { pathname } = useLocation();

  return (
    <StyledHeader>
      <StyledLeftContainer>
        <Link to="/">
          <JazzMeet fill="#ff4d00" />
        </Link>
        <StyledButtons>
          <Link to="/map" reloadDocument={pathname.slice(0, 4) === '/map'}>
            공연장
          </Link>
          <Link to="/show">공연</Link>
          <Link to="/inquiry">문의</Link>
        </StyledButtons>
      </StyledLeftContainer>

      <SearchBox />
    </StyledHeader>
  );
};

const StyledHeader = styled.header`
  width: 100%;
  height: 73px;
  padding: 0 42px;
  box-sizing: border-box;
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: #000;
`;

const StyledLeftContainer = styled.div`
  > a > svg {
    width: 140px;
    height: auto;
  }

  display: flex;
  gap: 34px;
`;

const StyledButtons = styled.nav`
  display: flex;
  align-items: center;
  gap: 34px;

  > a {
    color: #fff;
    cursor: pointer;
    text-decoration: none;
  }
`;
