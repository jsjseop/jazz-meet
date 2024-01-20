import { getVenueShowDates, getVenueShowsByDate } from 'apis/show';
import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { HasShowDates, ShowDetail } from 'types/api.types';
import { useShallow } from 'zustand/react/shallow';
import { useShowDetailStore } from '~/stores/useShowDetailStore';
import { Calendar } from './Calendar';
import { ShowList } from './ShowList';
import { useCalendar } from './useCalendar';

export const ShowInfo: React.FC = () => {
  const [hasShowDates, setHasShowDates] = useState<HasShowDates>();
  const { showDetailDate, setShowDetailDate } = useShowDetailStore(
    useShallow((state) => ({
      showDetailDate: state.showDetailDate,
      setShowDetailDate: state.setShowDetailDate,
    })),
  );
  const {
    calendarDate,
    selectedDate,
    goToPreviousMonth,
    goToNextMonth,
    selectDate,
    selectPreviousDate,
    selectNextDate,
  } = useCalendar(showDetailDate);
  const { venueId } = useParams();
  const [showList, setShowList] = useState<ShowDetail[]>([]);

  useEffect(() => {
    if (!venueId) return;

    const updateShowList = async () => {
      const shows = await getVenueShowsByDate({
        venueId,
        date: selectedDate,
      });
      setShowList(shows);
      setShowDetailDate(new Date());
    };

    updateShowList();
  }, [venueId, selectedDate, setShowDetailDate]);

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
