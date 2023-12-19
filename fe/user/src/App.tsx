import { LocalizationProvider } from '@mui/x-date-pickers';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { RouterProvider } from 'react-router-dom';
import { router } from 'router';
import { DeviceTypeChecker } from './components/DeviceTypeChecker';

export const App: React.FC = () => {
  return (
    // <ThemeProvider theme={designSystem}>
    <LocalizationProvider dateAdapter={AdapterDayjs}>
      <DeviceTypeChecker />
      <RouterProvider router={router} />
    </LocalizationProvider>
    // </ThemeProvider>
  );
};
