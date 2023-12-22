import { getVenueShowDates, getVenueShowsByDate } from 'apis/show';
import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { HasShowDates, ShowDetail } from 'types/api.types';
import { Calendar } from './Calendar';
import { ShowList } from './ShowList';
import { useCalendar } from './useCalendar';

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
      const shows = await getVenueShowsByDate({ venueId, date: selectedDate });
      setShowList(shows);
    };

    updateShowList();
  }, [venueId, selectedDate]);

  useEffect(() => {
    if (!venueId) return;

    const updateHasShowDates = async () => {
      const dates = await getVenueShowDates({ venueId, date: calendarDate });
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
        {...{
          showList,
          selectedDate,
          selectPreviousDate,
          selectNextDate,
        }}
      />
    </>
  );
};
