import styled from '@emotion/styled';
import { Calendar } from './Calendar';
import ChevronLeftIcon from '@mui/icons-material/ChevronLeft';
import ChevronRightIcon from '@mui/icons-material/ChevronRight';
import { useCalendar } from './useCalendar';
import { getKoreanWeekdayName } from '@utils/dateUtils';
import { useEffect, useState } from 'react';
import { ShowDetail } from 'types/api.types';
import { useParams } from 'react-router-dom';
import { getShowsByDate } from 'apis/show';

export const ShowInfo: React.FC = () => {
  const [showList, setShowList] = useState<ShowDetail[] | null>(null);
  const {
    calendarDate,
    selectedDate,
    goToPreviousMonth,
    goToNextMonth,
    selectDate,
    selectPreviousDate,
    selectNextDate,
  } = useCalendar();
  const { venueId } = useParams();

  useEffect(() => {
    if (!venueId) return;

    const updateShowList = async () => {
      const shows = await getShowsByDate({ venueId, date: selectedDate });
      setShowList(shows);
    };

    updateShowList();
  }, [venueId, selectedDate]);

  return (
    <>
      <Calendar
        {...{
          calendarDate,
          selectedDate,
          goToPreviousMonth,
          goToNextMonth,
          selectDate,
        }}
      />
      <StyledShowList>
        <StyledShowListHeader>
          <ChevronLeftIcon
            sx={{ fill: '#B5BEC6' }}
            onClick={selectPreviousDate}
          />
          <div>{`${
            selectedDate.getMonth() + 1
          }월 ${selectedDate.getDate()}일 ${getKoreanWeekdayName(
            selectedDate.getDay(),
          )}요일`}</div>
          <ChevronRightIcon sx={{ fill: '#B5BEC6' }} onClick={selectNextDate} />
        </StyledShowListHeader>
        <StyledShowListContent>
          <StyledShowListContentHeader>
            <div>시작</div>
            <div>종료</div>
          </StyledShowListContentHeader>

          {showList &&
            showList.map((show, index) => (
              <StyledShowListItem key={show.id}>
                <StyledShowListItemIndex>{index + 1}</StyledShowListItemIndex>
                <StyledShowListItemName>{show.teamName}</StyledShowListItemName>
                <StyledShowListItemTime>
                  {show.startTime}
                </StyledShowListItemTime>
                <StyledShowListItemTime>{show.endTime}</StyledShowListItemTime>
              </StyledShowListItem>
            ))}
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

  svg {
    cursor: pointer;
  }
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
