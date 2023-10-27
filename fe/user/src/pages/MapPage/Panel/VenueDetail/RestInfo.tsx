import styled from '@emotion/styled';
import { Calendar } from './Calendar';
import { Tabs } from './Tabs';
import { Tab } from './Tabs/Tab';
import ChevronLeftIcon from '@mui/icons-material/ChevronLeft';
import ChevronRightIcon from '@mui/icons-material/ChevronRight';

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
            <div></div>
            <div></div>
            <div>시작</div>
            <div>종료</div>

            <StyledShowListIndex>01</StyledShowListIndex>
            <StyledShowListName>권트리오</StyledShowListName>
            <div>12:00</div>
            <div>14:00</div>

            <StyledShowListIndex>02</StyledShowListIndex>
            <StyledShowListName>김철수 쿼텟</StyledShowListName>
            <div>12:00</div>
            <div>14:00</div>

            <StyledShowListIndex>03</StyledShowListIndex>
            <StyledShowListName>WE 필하모닉스의 피아노 퀀텟</StyledShowListName>
            <div>12:00</div>
            <div>14:00</div>

            <StyledShowListIndex>04</StyledShowListIndex>
            <StyledShowListName>러셀 말론 솔로 기타</StyledShowListName>
            <div>12:00</div>
            <div>14:00</div>
          </StyledShowListContent>
        </StyledShowList>
      </StyledRestInfoContent>
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
`;

const StyledShowList = styled.div`
  width: 100%;
  padding: 24px;
  border: 1px solid #dbdbdb;
  border-radius: 8px;
`;

const StyledShowListHeader = styled.div`
  font-size: 14px;
  margin-bottom: 22px;
  display: flex;
  justify-content: center;
  align-items: center;
`;

const StyledShowListContent = styled.div`
  display: grid;
  grid-template-columns: 40px 1fr 40px 40px;
  align-items: center;
  justify-items: center;
  gap: 14px;
`;

const StyledShowListIndex = styled.div`
  background-color: #d9d9d9;
  padding: 4px 8px;
  border-radius: 30px;
`;

const StyledShowListName = styled.div`
  justify-self: start;
`;
