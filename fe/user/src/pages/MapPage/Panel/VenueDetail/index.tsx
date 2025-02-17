import styled from '@emotion/styled';
import { IconButton } from '@mui/material';
import { useEffect, useState } from 'react';
import {
  Outlet,
  useLocation,
  useNavigate,
  useOutletContext,
  useParams,
} from 'react-router-dom';
import { getVenueDetail } from '~/apis/venue';
import CaretLeft from '~/assets/icons/CaretLeft.svg?react';
import { VenueDetailData } from '~/types/api.types';
import { BasicInfo } from './BasicInfo';
import { Header } from './Header';
import { Images } from './Images';
import { RestInfo } from './RestInfo';

export const VenueDetail: React.FC = () => {
  const { venueId } = useParams();
  const currentLocation = useLocation();
  const navigate = useNavigate();
  const mapElement = useOutletContext<React.RefObject<HTMLDivElement>>();
  const [isRender, setRender] = useState(false);
  const [data, setData] = useState<VenueDetailData>();

  const backToVenueList = () => {
    navigate(`/map?${currentLocation.search}`);
  };

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

  useEffect(() => {
    if (!data) {
      return;
    }

    setRender(true);
  }, [data]);

  return (
    <>
      {data && (
        <StyledVenueDetail isRender={isRender}>
          <Images images={data.images} />
          <IconButton
            type="button"
            sx={{ position: 'absolute', top: '0' }}
            onClick={backToVenueList}
          >
            <CaretLeft />
          </IconButton>
          <Header name={data.name} links={data.links} />
          <BasicInfo
            id={data.id}
            roadNameAddress={data.roadNameAddress}
            lotNumberAddress={data.lotNumberAddress}
            venueHours={data.venueHours}
            phoneNumber={data.phoneNumber}
            naverMapUrl={
              data.links.find((link) => link.type === 'naverMap')?.url
            }
          />
          <RestInfo description={data.description} />
          <Outlet context={mapElement} />
        </StyledVenueDetail>
      )}
    </>
  );
};

const StyledVenueDetail = styled.div<{ isRender: boolean }>`
  box-sizing: border-box;
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
