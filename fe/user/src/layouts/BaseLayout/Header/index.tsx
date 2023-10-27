import { SearchBar } from '@components/SearchBar';
import styled from '@emotion/styled';
import { Link } from 'react-router-dom';

export const Header: React.FC = () => {
  return (
    <StyledHeader>
      <StyledLeftContainer>
        <Link to="/">
          <StyledLogoImage src="https://github.com/jsh3418/js-calculator-bonobono/assets/57666791/07da94d4-01b3-4e70-8973-db44780f6d6e" />
        </Link>
        <StyledButtons>
          <Link to="/map">지도</Link>
          <Link to="/inquiry">문의</Link>
        </StyledButtons>
      </StyledLeftContainer>

      <SearchBar />
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
  display: flex;
  gap: 34px;
`;

const StyledLogoImage = styled.img`
  width: 146px;
  height: 27px;
  object-fit: cover;
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
