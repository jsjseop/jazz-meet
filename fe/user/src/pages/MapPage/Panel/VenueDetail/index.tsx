import styled from '@emotion/styled';
import { Header } from './Header';
import { BasicInfo } from './BasicInfo';
import { RestInfo } from './RestInfo';
import { useEffect, useState } from 'react';
import { Outlet, useOutletContext, useParams } from 'react-router-dom';
import { Images } from './Images';
import { VenueDetailData } from '~/types/api.types';
import { getVenueDetail } from '~/apis/venue';

export const VenueDetail: React.FC = () => {
  const { venueId } = useParams();
  const mapRef = useOutletContext<React.RefObject<HTMLDivElement>>();
  const [isRender, setRender] = useState(false);
  const [data, setData] = useState<VenueDetailData>();

  useEffect(() => {
    setRender(true);
  }, []);

  useEffect(() => {
    if (!venueId) {
      return;
    }

    const updateDate = async () => {
      const venueDetailData = await getVenueDetail(venueId);
      setData(venueDetailData);
    };

    updateDate();
  }, [venueId]);

  return (
    <>
      {data && (
        <StyledVenueDetail isRender={isRender}>
          <Images images={data.images} />
          <Header name={data.name} links={data.links} />
          <BasicInfo
            roadNameAddress={data.roadNameAddress}
            lotNumberAddress={data.lotNumberAddress}
            venueHours={data.venueHours}
            phoneNumber={data.phoneNumber}
            naverMapUrl={
              data.links.find((link) => link.type === 'naverMap')?.url
            }
          />
          <RestInfo description={data.description} />
          <Outlet context={mapRef} />
          {/* {showInfoDetail && createPortal(<Images />)} */}
        </StyledVenueDetail>
      )}
    </>
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
