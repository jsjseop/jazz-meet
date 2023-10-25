import { ThemeProvider } from '@emotion/react';
import { designSystem } from '@styles/designSystem';
import { RouterProvider } from 'react-router-dom';
import { router } from 'router';

export const App: React.FC = () => {
  return (
    // <ThemeProvider theme={designSystem}>
      <RouterProvider router={router} />
    // </ThemeProvider>
  );
};
