import { Outlet } from 'react-router-dom';
import { Header } from './Header';

export const BaseLayout: React.FC = () => {
  return (
    <>
      <Header />
      <Outlet />
    </>
  );
};
