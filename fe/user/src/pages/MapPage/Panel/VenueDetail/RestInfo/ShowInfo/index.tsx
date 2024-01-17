import { getVenueShowDates, getVenueShowsByDate } from 'apis/show';
import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { HasShowDates } from 'types/api.types';
import { useDateFromSearchParams } from '~/hooks/useDateFromSearchParams';
import { useShowDetailStore } from '~/stores/useShowDetailStore';
import { Calendar } from './Calendar';
import { ShowList } from './ShowList';
import { useCalendar } from './useCalendar';

export const ShowInfo: React.FC = () => {
  const [hasShowDates, setHasShowDates] = useState<HasShowDates>();
  const dateFromSearchParams = useDateFromSearchParams();
  const {
    calendarDate,
    selectedDate,
    goToPreviousMonth,
    goToNextMonth,
    selectDate,
    selectPreviousDate,
    selectNextDate,
  } = useCalendar(dateFromSearchParams);
  const { venueId } = useParams();
  const { showDetails, setShowDetails } = useShowDetailStore();

  useEffect(() => {
    if (!venueId) return;

    const updateShowList = async () => {
      const shows = await getVenueShowsByDate({
        venueId,
        date: selectedDate,
      });
      setShowDetails(shows);
    };

    updateShowList();
  }, [venueId, selectedDate, setShowDetails]);

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
          showList: showDetails,
          selectedDate,
          selectPreviousDate,
          selectNextDate,
        }}
      />
    </>
  );
};
