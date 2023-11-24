import styled from '@emotion/styled';
import { Outlet } from 'react-router-dom';
import { Sidebar } from './Sidebar';

export const AdminLayout: React.FC = () => {
  return (
    <StyledLayout>
      <Sidebar />
      <Outlet />
    </StyledLayout>
  );
};

const StyledLayout = styled.div`
  display: flex;
  height: 100vh;
`;
