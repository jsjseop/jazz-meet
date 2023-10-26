import { LocalizationProvider } from '@mui/x-date-pickers';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { RouterProvider } from 'react-router-dom';
import { router } from 'router';

export const App: React.FC = () => {
  return (
    // <ThemeProvider theme={designSystem}>
    <LocalizationProvider dateAdapter={AdapterDayjs}>
      <RouterProvider router={router} />
    </LocalizationProvider>
    // </ThemeProvider>
  );
};
