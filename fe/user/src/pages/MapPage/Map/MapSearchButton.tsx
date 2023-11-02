import styled from '@emotion/styled';
import RefreshIcon from '@mui/icons-material/Refresh';
import { useNavigate } from 'react-router-dom';

type Props = {
  map: React.MutableRefObject<naver.maps.Map | undefined>;
  hideMapSearchButton: () => void;
};

export const MapSearchButton: React.FC<Props> = ({ map, hideMapSearchButton }) => {
  const navigate = useNavigate();


   const onMapSearchButtonClick = () => {
    if (!map.current) {
      throw new Error('map is not initialized');
    }

    const bounds = map.current.getBounds();

    if (!(bounds instanceof naver.maps.LatLngBounds)) {
      return;
    }

    navigate(
      `/map?lowLatitude=${bounds.south()}&highLatitude=${bounds.north()}&lowLongitude=${bounds.west()}&highLongitude=${bounds.east()}`,
    );

    hideMapSearchButton();
  };

  return (
    <StyledMapSearchButton onClick={onMapSearchButtonClick} >
      <RefreshIcon />
      <span>현 지도에서 검색</span>
    </StyledMapSearchButton>
  );
};

const StyledMapSearchButton = styled.button`
  position: absolute;
  bottom: 8%;
  left: 50%;
  transform: translateX(-50%);
  z-index: 1000;
  padding: 12px 16px;
  border-radius: 50px;
  display: flex;
  align-items: center;
  gap: 9px;
  color: #fff;
  background-color: #0775f4;

  &:hover {
    cursor: pointer;
    background-color: #0a84ff;
  }
`;
