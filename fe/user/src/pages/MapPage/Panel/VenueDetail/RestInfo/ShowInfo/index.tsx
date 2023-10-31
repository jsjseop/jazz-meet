import styled from '@emotion/styled';
import { Calendar } from './Calendar';
import ChevronLeftIcon from '@mui/icons-material/ChevronLeft';
import ChevronRightIcon from '@mui/icons-material/ChevronRight';
import { useCalendar } from './useCalendar';
import { getWeekDay } from '@utils/dateUtils';

export const ShowInfo: React.FC = () => {
  const { currentDate, selectedDate, prevMonth, nextMonth, selectDate } =
    useCalendar();

  return (
    <>
      <Calendar
        {...{ currentDate, selectedDate, prevMonth, nextMonth, selectDate }}
      />
      <StyledShowList>
        <StyledShowListHeader>
          <ChevronLeftIcon sx={{ fill: '#B5BEC6' }} />
          <div>{`${
            selectedDate.getMonth() + 1
          }월 ${selectedDate.getDate()}일 ${getWeekDay(
            selectedDate.getDay(),
          )}요일`}</div>
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
            <StyledShowListItemName>러셀 말론 솔로 기타</StyledShowListItemName>
            <StyledShowListItemTime>12:00</StyledShowListItemTime>
            <StyledShowListItemTime>14:00</StyledShowListItemTime>
          </StyledShowListItem>
        </StyledShowListContent>
      </StyledShowList>
    </>
  );
};

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
