import styled from '@emotion/styled';
import DateRangeIcon from '@mui/icons-material/DateRange';
import IconButton from '@mui/material/IconButton';
import { DateCalendar } from '@mui/x-date-pickers';
import { useState } from 'react';

export const DatePicker: React.FC = () => {
  const [calendarOpen, setCalendarOpen] = useState(false);

  return (
    <StyledDatePicker>
      <IconButton size="large" onClick={() => setCalendarOpen((co) => !co)}>
        <DateRangeIcon fontSize="inherit" />
      </IconButton>

      {calendarOpen && (
        <DateCalendar
          sx={{
            position: 'absolute',
            right: 0,
            transform: 'translateY(8px)',
            boxShadow: '0 4px 4px rgba(0, 0, 0, 0.25)',
          }}
        />
      )}
    </StyledDatePicker>
  );
};

const StyledDatePicker = styled.div`
  position: relative;
`;
