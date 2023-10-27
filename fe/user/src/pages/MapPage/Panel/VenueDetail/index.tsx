import styled from '@emotion/styled';
import { PreviewImages } from './PreviewImages';
import { Header } from './Header';
import { BasicInfo } from './BasicInfo';
import { RestInfo } from './RestInfo';
import { useEffect, useState } from 'react';

export const VenueDetail: React.FC = () => {
  const [isRender, setRender] = useState(false);

  useEffect(() => {
    setRender(true);
  }, []);

  return (
    <StyledVenueDetail isRender={isRender}>
      <PreviewImages />
      <Header />
      <BasicInfo />
      <RestInfo />

      {/* {showInfoDetail && <InfoDetail />} */}
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
