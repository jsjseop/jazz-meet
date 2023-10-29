import styled from '@emotion/styled';
import { useEffect } from 'react';

type Props = {
  mapRef: React.RefObject<HTMLDivElement>;
};

export const Map: React.FC<Props> = ({ mapRef }) => {
  useEffect(() => {
    const mapOptions = {
      center: new naver.maps.LatLng(37.5666103, 126.9783882),
    };

    new naver.maps.Map('map', mapOptions);
  }, []);

  return <StyledMap id="map" ref={mapRef} />;
};

const StyledMap = styled.div`
  width: 100%;
  height: inherit;
`;
