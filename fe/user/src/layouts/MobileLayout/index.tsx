import { Outlet } from 'react-router-dom';
import { BottomNavigation } from './BottomNavigation';
import { Header } from './Header';

export const MobileLayout: React.FC = () => {
  return (
    <>
      <Header />
      <Outlet />
      <BottomNavigation />
    </>
  );
};
