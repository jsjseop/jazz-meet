import { useEffect, useMemo, useState } from 'react';
import { useSearchParams } from 'react-router-dom';
import { getDateFromFormattedDate } from '~/utils/dateUtils';

export const useDateFromSearchParams = () => {
  const [searchParams] = useSearchParams();
  const [date, setDate] = useState<string | null>(null);

  useEffect(() => {
    setDate(searchParams.get('date'));
  }, [searchParams]);

  return useMemo(
    () => (date ? getDateFromFormattedDate(date) : new Date()),
    [date],
  );
};
