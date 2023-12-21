import styled from '@emotion/styled';
import ListAltIcon from '@mui/icons-material/ListAlt';
import MapOutlinedIcon from '@mui/icons-material/MapOutlined';
import { useEffect, useRef, useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import MyLocation from '~/assets/icons/MyLocation.svg';
import { BASIC_COORDINATE } from '~/constants/MAP';
import { BOTTOM_NAVIGATION_Z_INDEX } from '~/constants/Z_INDEX';
import { useMapDataUpdater } from '~/hooks/useMapDataUpdater';
import { useUserCoordinate } from '~/hooks/useUserCoordinate';
import { useDeviceTypeStore } from '~/stores/useDeviceTypeStore';
import { RenderType } from '~/types/device.types';
import { getQueryString } from '~/utils/getQueryString';
import {
  addMapButton,
  getMapBounds,
  getQueryBounds,
  panToCoordinates,
} from '~/utils/map';
import { Map } from './Map';
import { Panel } from './Panel';

export const MapPage: React.FC = () => {
  const [map, setMap] = useState<naver.maps.Map>();
  const isMobile = useDeviceTypeStore((state) => state.deviceType.isMobile);
  const [renderType, setRenderType] = useState<RenderType>(
    isMobile ? 'map' : 'all',
  );
  const renderTypeToList = () => {
    if (isMobile) {
      setRenderType('list');
    }
  };
  const {
    searchedVenues,
    handleChangeVenueListPage,
    handleUpdateMapDataWithBounds,
    handleUpdateMapDataWithWord,
    handleUpdateMapDataWithVenueId,
  } = useMapDataUpdater({ map, renderTypeToList });
  const mapElement = useRef<HTMLDivElement>(null);

  const { userCoordinate } = useUserCoordinate();

  const { search } = useLocation();
  const navigate = useNavigate();
  const word = new URLSearchParams(search).get('word');
  const venueId = new URLSearchParams(search).get('venueId');

  const navigateWithMapBounds = () => {
    if (!map) {
      return;
    }

    const bounds = getMapBounds(map);

    if (!bounds) {
      return;
    }

    navigate(`/map${getQueryString(bounds)}`);
  };

  useEffect(() => {
    if (!map) {
      return;
    }

    if (word) {
      handleUpdateMapDataWithWord(word);

      return;
    }

    if (venueId) {
      handleUpdateMapDataWithVenueId(venueId);
      navigate(`/map/venues/${venueId}${search}`);

      return;
    }

    const bounds = getQueryBounds(search);

    handleUpdateMapDataWithBounds(bounds);
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [map, word, venueId, search]);

  useEffect(() => {
    if (!map) {
      return;
    }

    const buttonHTMLString = `<div class=${MY_LOCATION_BUTTON}><img src=${MyLocation} alt='현재 위치로 이동' /></div>`;
    const position = naver.maps.Position.RIGHT_BOTTOM;
    const coordinate = userCoordinate ?? BASIC_COORDINATE;
    const onClick = () => {
      panToCoordinates([coordinate], map);
    };

    addMapButton({ map, buttonHTMLString, position, onClick });
  }, [map, userCoordinate]);

  return (
    <StyledMapPage isMobile={isMobile}>
      <Map
        mapElement={mapElement}
        renderType={renderType}
        onMapInitialized={(map: naver.maps.Map) => setMap(map)}
        onCurrentViewSearchClick={navigateWithMapBounds}
      />

      <Panel
        mapElement={mapElement}
        searchedVenus={searchedVenues}
        renderType={renderType}
        handleChangeVenueListPage={handleChangeVenueListPage}
      />

      {renderType !== 'all' ? (
        renderType === 'map' ? (
          <StyledRenderToggleButton onClick={() => setRenderType('list')}>
            <div>리스트 보기</div>
            <ListAltIcon />
          </StyledRenderToggleButton>
        ) : (
          <StyledRenderToggleButton onClick={() => setRenderType('map')}>
            <div>지도 보기</div>
            <MapOutlinedIcon />
          </StyledRenderToggleButton>
        )
      ) : null}
    </StyledMapPage>
  );
};

const MY_LOCATION_BUTTON = 'my-location-button';

const StyledMapPage = styled.div<{ isMobile: boolean }>`
  overflow: hidden;
  height: ${({ isMobile }) =>
    isMobile ? 'calc(100vh - 73px - 47px)' : 'calc(100vh - 73px)'};
  display: flex;

  .${MY_LOCATION_BUTTON} {
    width: 35px;
    height: 35px;
    user-select: none;
    background-color: #ffffff;
    border-radius: 5px;
    border: 1px solid #dbdbdb;
    padding: 5px;
    box-sizing: border-box;
    margin: 30px 10px;
    cursor: pointer;

    &:active {
      background-color: #dbdbdb;
    }

    & img {
      width: 100%;
      height: 100%;
    }
  }
`;

const StyledRenderToggleButton = styled.div`
  border-radius: 5px;
  background-color: #47484e;
  border: 1px solid #dbdbdb;
  padding: 10px;
  box-sizing: border-box;
  color: #ffffff;

  display: flex;
  align-items: center;
  justify-content: center;
  gap: 5px;

  position: fixed;
  bottom: 65px;
  left: 50%;
  transform: translateX(-50%);
  z-index: ${BOTTOM_NAVIGATION_Z_INDEX};
`;
