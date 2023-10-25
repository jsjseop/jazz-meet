import styled from '@emotion/styled';
import { Header } from 'layouts/BaseLayout/Header';
import { Outlet } from 'react-router-dom';

export const BaseLayout: React.FC = () => {
  return (
    <BaseLayoutWrapper>
      <Header />
      <Outlet />
    </BaseLayoutWrapper>
  );
};

const BaseLayoutWrapper = styled.div`
  display: flex;
  justify-content: center;
`
