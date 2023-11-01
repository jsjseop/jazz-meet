import styled from '@emotion/styled';
import { useEffect } from 'react';
import { useLocation } from 'react-router-dom';
import { MARKER_SVG } from '@constants/MAP';
import { getVenuePinsBySearch } from 'apis/venue';

type Props = {
  mapRef: React.RefObject<HTMLDivElement>;
};

export const Map: React.FC<Props> = ({ mapRef }) => {
  const { search } = useLocation();

  useEffect(() => {
    (async () => {
      const mapOptions = {
        center: new naver.maps.LatLng(37.5666103, 126.9783882),
      };

      const map = new naver.maps.Map('map', mapOptions);

      if (!search) return;

      const pins = await getVenuePinsBySearch(search);

      if (pins.length === 0) return;

      const boundary = new naver.maps.LatLngBounds(
        new naver.maps.LatLng(37.5013976004701, 127.0329803750662),
        new naver.maps.LatLng(37.55721930476495, 126.90496892094431),
      );

      pins.forEach((pin) => {
        boundary.extend(new naver.maps.LatLng(pin.latitude, pin.longitude));

        new naver.maps.Marker({
          position: new naver.maps.LatLng(pin.latitude, pin.longitude),
          map: map,
          icon: {
            content: MARKER_SVG,
            size: new naver.maps.Size(10, 48),
          },
        });
      });

      map.fitBounds(boundary);
    })();
  }, [search]);

  return <StyledMap id="map" ref={mapRef} />;
};

const StyledMap = styled.div`
  width: 100%;
  height: inherit;
`;
