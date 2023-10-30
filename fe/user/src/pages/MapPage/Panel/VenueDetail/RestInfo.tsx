import styled from '@emotion/styled';
import { Calendar } from './Calendar';
import { Tabs } from './Tabs';
import { Tab } from './Tabs/Tab';
import ChevronLeftIcon from '@mui/icons-material/ChevronLeft';
import ChevronRightIcon from '@mui/icons-material/ChevronRight';
import LocalCafeOutlinedIcon from '@mui/icons-material/LocalCafeOutlined';

export const RestInfo: React.FC = () => {
  return (
    <StyledRestInfo>
      <Tabs>
        <Tab isSelected>공연정보</Tab>
        <Tab>판매정보</Tab>
        <Tab>공연장정보</Tab>
      </Tabs>

      <StyledRestInfoContent>
        <Calendar />
        <StyledShowList>
          <StyledShowListHeader>
            <ChevronLeftIcon sx={{ fill: '#B5BEC6' }} />
            <div>10월 17일 화요일</div>
            <ChevronRightIcon sx={{ fill: '#B5BEC6' }} />
          </StyledShowListHeader>
          <StyledShowListContent>
            <StyledShowListContentHeader>
              <div>시작</div>
              <div>종료</div>
            </StyledShowListContentHeader>

            <StyledShowListItem>
              <StyledShowListItemIndex>01</StyledShowListItemIndex>
              <StyledShowListItemName>권트리오</StyledShowListItemName>
              <StyledShowListItemTime>12:00</StyledShowListItemTime>
              <StyledShowListItemTime>14:00</StyledShowListItemTime>
            </StyledShowListItem>

            <StyledShowListItem>
              <StyledShowListItemIndex>02</StyledShowListItemIndex>
              <StyledShowListItemName>김철수 쿼텟</StyledShowListItemName>
              <StyledShowListItemTime>12:00</StyledShowListItemTime>
              <StyledShowListItemTime>14:00</StyledShowListItemTime>
            </StyledShowListItem>

            <StyledShowListItem>
              <StyledShowListItemIndex>03</StyledShowListItemIndex>
              <StyledShowListItemName>
                WE 필하모닉스의 피아노 퀀텟
              </StyledShowListItemName>
              <StyledShowListItemTime>12:00</StyledShowListItemTime>
              <StyledShowListItemTime>14:00</StyledShowListItemTime>
            </StyledShowListItem>

            <StyledShowListItem>
              <StyledShowListItemIndex>04</StyledShowListItemIndex>
              <StyledShowListItemName>
                러셀 말론 솔로 기타
              </StyledShowListItemName>
              <StyledShowListItemTime>12:00</StyledShowListItemTime>
              <StyledShowListItemTime>14:00</StyledShowListItemTime>
            </StyledShowListItem>
          </StyledShowListContent>
        </StyledShowList>
      </StyledRestInfoContent>

      <Tabs>
        <Tab isSelected>공연장정보</Tab>
      </Tabs>
      <StyledContentContainer>
        <StyledContent>
          <LocalCafeOutlinedIcon
            sx={{ width: '28px', height: '28px', fill: '#848484' }}
          />
          <StyledBasicInfoText>
            올댓재즈는 1976년부터 한국의 재즈씬을 이끌어온 한국의 대표브랜드로서
            재즈 매니아들의 성지와도 같은 공간입니다.우리는 재즈의 불모지로
            불리는 한국에서 재즈 장르의 음악이 조금 더 대중들...
          </StyledBasicInfoText>
        </StyledContent>
      </StyledContentContainer>
    </StyledRestInfo>
  );
};

const StyledRestInfo = styled.div`
  padding: 30px 0;
`;

const StyledRestInfoContent = styled.div`
  padding: 22px 54px;
  display: flex;
  gap: 11px;

  @media screen and (max-width: 1500px) {
    flex-direction: column;
  }
`;

const StyledShowList = styled.div`
  width: 100%;
  padding: 24px;
  border: 1px solid #dbdbdb;
  border-radius: 8px;
  box-sizing: border-box;
`;

const StyledShowListHeader = styled.div`
  font-size: 14px;
  margin-bottom: 22px;
  display: flex;
  justify-content: center;
  align-items: center;
`;

const StyledShowListContent = styled.div`
  display: flex;
  flex-direction: column;
  gap: 14px;
`;

const StyledShowListContentHeader = styled.div`
  display: flex;
  justify-content: end;
  gap: 14px;

  > div {
    width: 40px;
    text-align: center;
  }
`;

const StyledShowListItem = styled.div`
  display: flex;
  align-items: center;
  gap: 14px;
`;

const StyledShowListItemIndex = styled.div`
  width: 40px;
  background-color: #d9d9d9;
  padding: 4px 8px;
  border-radius: 30px;
  display: flex;
  align-items: center;
  justify-content: center;
`;

const StyledShowListItemName = styled.div`
  width: 100%;
  justify-self: start;
`;

const StyledShowListItemTime = styled.div`
  width: 40px;
`;

const StyledContentContainer = styled.div`
  padding: 24px 30px;
  display: flex;
  flex-direction: column;
`;

const StyledContent = styled.div`
  display: flex;
  gap: 19px;
  align-items: center;

  > svg {
    align-self: flex-start;
  }
`;

const StyledBasicInfoText = styled.div`
  font-size: 20px;
  line-height: 150%;
`;
