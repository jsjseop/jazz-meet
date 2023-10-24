import { SearchBar } from '@components/SearchBar';
import styled from '@emotion/styled';

export const Header: React.FC = () => {
  return (
    <StyledHeader>
      <LeftContainer>
        <div>Jazz Meet Logo</div>
        <Buttons>
          <button>지도</button>
          <button>문의</button>
        </Buttons>
      </LeftContainer>

      <SearchBar />
    </StyledHeader>
  );
};

const StyledHeader = styled.div`
  width: 100%;
  display: flex;
  justify-content: space-between;
`;

const LeftContainer = styled.div`
  display: flex;
`;

const Buttons = styled.nav`
  display: flex;
`;
