import { Outlet } from 'react-router-dom';
import { Sidebar } from './Sidebar';

export const AdminLayout: React.FC = () => {
  return (
    <>
      <Sidebar />
      <Outlet />
    </>
  );
};
