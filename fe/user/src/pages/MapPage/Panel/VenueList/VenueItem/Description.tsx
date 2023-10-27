import styled from '@emotion/styled';

export const Description: React.FC = () => {
  const introduction =
    '인스타그램 : @yeonnam5701\n -재즈음악과 함께 와인을 즐길수 있는공간입니다,\n -와인콜키지는 따로 안되고 있습니다, \n-미성년자는 출입이 불가합니다 .\n -캐치테이블로 예약후 자리는 오시는순서로 원하시는 자리 앉는 방식입니다.\n -캐치테이블 예약금은 오시면 반환되고 따로 입장료 1만원 입니다.\n -주차공간은 6대까지 건물에 가능합니다. 전화문의따로 주시면 됩니다.\n -늘 좋은음악과 좋은 와인으로 함께합니다.';

  return (
    <StyledDescription>
      <StyledHeader>
        <StyledName>연남5701</StyledName>
        <StyledAddress>서울시 마포구 교동로23길 64</StyledAddress>
      </StyledHeader>

      <StyledMainContent>
        <StyledIntroduction>{introduction}</StyledIntroduction>
        <StyledSchedule>
          <StyledShowTime>
            <span>1부</span>
            <span>19:30~20:30</span>
          </StyledShowTime>
          <StyledShowTime>
            <span>2부</span>
            <span>21:00~22:00</span>
          </StyledShowTime>
        </StyledSchedule>
      </StyledMainContent>
    </StyledDescription>
  );
};

const StyledDescription = styled.article`
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 75px;
`;

const StyledHeader = styled.header`
  display: flex;
  flex-direction: column;
  gap: 8px;
`;

const StyledName = styled.h3`
  font-size: 24px;
  font-weight: 800;
  color: #1b1b1b;
`;

const StyledAddress = styled.span`
  font-size: 20px;
  font-weight: 500;
  color: #848484;
`;

const StyledMainContent = styled.section`
  display: flex;
  flex-direction: column;
  gap: 11px;
`;

const StyledIntroduction = styled.p`
  font-size: 20px;
  font-weight: 500;
  color: #5B5B5B;
  line-height: 1.3;
  letter-spacing: -4%;
  white-space: pre-line;
  display: -webkit-box;
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;
  overflow: hidden;
`;

const StyledSchedule = styled.div`
  & * {
    font-size: 18px;
    font-weight: 400;
    color: #c6c6c6;
  }
`;

const StyledShowTime = styled.div`
  display: flex;
  gap: 8px;
`;
