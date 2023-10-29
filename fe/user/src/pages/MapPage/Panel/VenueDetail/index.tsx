import styled from '@emotion/styled';
import { Header } from './Header';
import { BasicInfo } from './BasicInfo';
import { RestInfo } from './RestInfo';
import { useEffect, useState } from 'react';
import { Outlet, useOutletContext } from 'react-router-dom';
import { Images } from './Images';

export const VenueDetail: React.FC = () => {
  const mapRef = useOutletContext<React.RefObject<HTMLDivElement>>();
  const [isRender, setRender] = useState(false);

  useEffect(() => {
    setRender(true);
  }, []);

  return (
    <StyledVenueDetail isRender={isRender}>
      <Images />
      <Header />
      <BasicInfo />
      <RestInfo />
      <Outlet context={mapRef} />
      {/* {showInfoDetail && createPortal(<Images />)} */}
    </StyledVenueDetail>
  );
};

const StyledVenueDetail = styled.div<{ isRender: boolean }>`
  width: 100%;
  height: 100%;
  overflow-y: auto;
  position: absolute;
  left: 100%;
  transition: left 0.5s ease-in-out;
  ${({ isRender }) => (isRender ? 'left: 0;' : '')}
  background-color: #fff;

  &::-webkit-scrollbar {
    width: 18px;
  }

  &::-webkit-scrollbar-thumb {
    background-clip: padding-box;
    background-color: #c1c1c1;
    border-radius: 10px;
    border: 4px solid transparent;
  }

  &::-webkit-scrollbar-track {
    background-color: transparent;
  }
`;
