import { SearchBar } from '@components/SearchBar';
import styled from '@emotion/styled';

export const Header: React.FC = () => {
  return (
    <StyledHeader>
      <LeftContainer>
        <LogoImage src="https://github.com/jsh3418/js-calculator-bonobono/assets/57666791/07da94d4-01b3-4e70-8973-db44780f6d6e" />
        <Buttons>
          <button>지도</button>
          <button>문의</button>
        </Buttons>
      </LeftContainer>

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

const LeftContainer = styled.div`
  display: flex;
  gap: 34px;
`;

const LogoImage = styled.img`
  width: 146px;
  height: 27px;
  object-fit: cover;
`;

const Buttons = styled.nav`
  display: flex;
  gap: 34px;

  > button {
    color: #fff;
  }
`;
