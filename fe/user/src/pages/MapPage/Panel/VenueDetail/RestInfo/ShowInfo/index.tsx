import { Calendar } from './Calendar';
import { useCalendar } from './useCalendar';
import { useEffect, useState } from 'react';
import { HasShowDates, ShowDetail } from 'types/api.types';
import { useParams } from 'react-router-dom';
import { getHasShowDates, getShowsByDate } from 'apis/show';
import { ShowList } from './ShowList';

export const ShowInfo: React.FC = () => {
  const [showList, setShowList] = useState<ShowDetail[]>();
  const [hasShowDates, setHasShowDates] = useState<HasShowDates>();
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

  useEffect(() => {
    if (!venueId) return;

    const updateHasShowDates = async () => {
      const dates = await getHasShowDates({ venueId, date: calendarDate });
      setHasShowDates(dates);
    };

    updateHasShowDates();
  }, [venueId, calendarDate]);

  return (
    <>
      <Calendar
        {...{
          calendarDate,
          selectedDate,
          eventDates: hasShowDates?.hasShow,
          goToPreviousMonth,
          goToNextMonth,
          selectDate,
        }}
      />
      <ShowList
        {...{ showList, selectedDate, selectPreviousDate, selectNextDate }}
      />
    </>
  );
};
