import { Outlet } from 'react-router-dom';
import { Header } from './Header';

export const PCLayout: React.FC = () => {
  return (
    <>
      <Header />
      <Outlet />
    </>
  );
};
