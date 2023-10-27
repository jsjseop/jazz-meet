import styled from '@emotion/styled';
import { useEffect } from 'react';

export const Map: React.FC = () => {
  useEffect(() => {
    const mapOptions = {
      center: new naver.maps.LatLng(37.5666103, 126.9783882),
    };

    new naver.maps.Map('map', mapOptions);
  }, []);

  return <StyledMap id="map" />;
};

const StyledMap = styled.div`
  width: 100%;
  height: inherit;
`;
