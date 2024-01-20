import styled from '@emotion/styled';
import ChevronLeftIcon from '@mui/icons-material/ChevronLeft';
import ChevronRightIcon from '@mui/icons-material/ChevronRight';
import { useEffect, useState } from 'react';
import { useShallow } from 'zustand/react/shallow';
import { useShowDetailStore } from '~/stores/useShowDetailStore';
import { ShowDetail as ShowDetailData } from '~/types/api.types';
import { getKoreanWeekdayName } from '~/utils/dateUtils';
import { ShowDetail } from '../../ShowDetail';

type Props = {
  showList: ShowDetailData[];
  selectedDate: Date;
  selectPreviousDate: () => void;
  selectNextDate: () => void;
};

export const ShowList: React.FC<Props> = ({
  showList,
  selectedDate,
  selectPreviousDate,
  selectNextDate,
}) => {
  const { showDetailId, setShowDetailId } = useShowDetailStore(
    useShallow((state) => ({
      showDetailId: state.showDetailId,
      setShowDetailId: state.setShowDetailId,
    })),
  );
  const [isShowDetailOpen, setIsShowDetailOpen] = useState(false);

  const month = selectedDate.getMonth() + 1;
  const date = selectedDate.getDate();
  const weekName = getKoreanWeekdayName(selectedDate.getDay());
  const dateString = `${month}월 ${date}일 ${weekName}요일`;

  useEffect(() => {
    setIsShowDetailOpen(showDetailId !== 0);
  }, [showDetailId]);

  // 선택된 날짜가 바뀌고 showList가 업데이트되면 첫번째 show를 선택
  useEffect(() => {
    if (showList.length > 0 && isShowDetailOpen) {
      setShowDetailId(showList[0].id);
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [showList]);

  return (
    <StyledShowList>
      <StyledShowListHeader>
        <ChevronLeftIcon
          sx={{ fill: '#B5BEC6' }}
          onClick={selectPreviousDate}
        />
        <div>{dateString}</div>
        <ChevronRightIcon sx={{ fill: '#B5BEC6' }} onClick={selectNextDate} />
      </StyledShowListHeader>
      <StyledShowListContent>
        <StyledShowListContentHeader>
          <div>시작</div>
          <div>종료</div>
        </StyledShowListContentHeader>

        {showList.map((show, index) => (
          <StyledShowListItem
            key={show.id}
            onClick={() => setShowDetailId(show.id)}
            $active={show.id === showDetailId}
          >
            <StyledShowListItemIndex>
              {String(index + 1).padStart(2, '0')}
            </StyledShowListItemIndex>
            <StyledShowListItemName>{show.teamName}</StyledShowListItemName>
            <StyledShowListItemTime>
              {formatTime(show.startTime)}
            </StyledShowListItemTime>
            <StyledShowListItemTime>
              {formatTime(show.endTime)}
            </StyledShowListItemTime>
          </StyledShowListItem>
        ))}
      </StyledShowListContent>

      {isShowDetailOpen && <ShowDetail showList={showList} />}
    </StyledShowList>
  );
};

const formatTime = (time: string) => {
  const date = new Date(time);

  return `${date.getHours().toString().padStart(2, '0')}:${date
    .getMinutes()
    .toString()
    .padStart(2, '0')}`;
};

const StyledShowList = styled.div`
  min-width: 300px;
  max-width: 400px;
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

const StyledShowListItem = styled.div<{ $active: boolean }>`
  display: flex;
  align-items: center;
  gap: 14px;
  ${({ $active }) => $active && `background-color: #f2f2f2;`};

  &:hover {
    background-color: #f2f2f2;
    cursor: pointer;
  }
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
