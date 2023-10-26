import styled from '@emotion/styled';

export const Footer: React.FC = () => {
  return (
    <StyledFooter>
      <FooterContent>
        <div>
          <div>JAZZ MEET</div>
          <div>서울 오피스</div>
          <div>서울특별시 용산구 서빙고로 17(한강로3가)</div>
          <div>뉴욕 오피스</div>
          <div>서울특별시 용산구 서빙고로 17(한강로3가)</div>
        </div>
        <div>
          <div>서울특별시 용산구 서빙고로 17(한강로3가)</div>
          <div>서울특별시 용산구 서빙고로 17(한강로3가)</div>
          <div>서울특별시 용산구 서빙고로 17(한강로3가)</div>
        </div>
      </FooterContent>
    </StyledFooter>
  );
};

const StyledFooter = styled.div`
  background-color: #000;
  color: #fff;
  padding: 90px 0;
`;

const FooterContent = styled.div`
  max-width: 1200px;
  margin: 0 auto;
  display: flex;

  justify-content: space-between;
`;
