import styled from '@emotion/styled';
import { Header } from 'layouts/BaseLayout/Header';
import { Outlet } from 'react-router-dom';

export const BaseLayout: React.FC = () => {
  return (
    <>
      <Header />
      <Outlet />
    </>
  );
};
