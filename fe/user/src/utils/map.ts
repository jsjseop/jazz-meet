import { MARKER_SVG, MARKER_SVG2 } from '~/constants/MAP';
import { Pin } from '~/types/api.types';

export const fitBoundsToPins = (pins: Pin[], map: naver.maps.Map) => {
  if (pins.length === 0) {
    return;
  }

  if (pins.length === 1) {
    map.setCenter(new naver.maps.LatLng(pins[0].latitude, pins[0].longitude));

    return;
  }

  const bounds = new naver.maps.LatLngBounds(
    new naver.maps.LatLng(pins[0].latitude, pins[0].longitude),
    new naver.maps.LatLng(pins[1].latitude, pins[1].longitude),
  );

  pins.forEach((pin) => {
    bounds.extend(new naver.maps.LatLng(pin.latitude, pin.longitude));
  });

  map.fitBounds(bounds);
};

export const addPinsOnMap = (
  pins: Pin[],
  map: naver.maps.Map,
  icon: 'marker' | 'pin',
) => {
  const obj = {
    marker: {
      content: MARKER_SVG,
      anchor: new naver.maps.Point(3, 48),
    },
    pin: {
      content: MARKER_SVG2,
      anchor: new naver.maps.Point(4.5, 7),
    },
  };

  pins.forEach((pin) => {
    new naver.maps.Marker({
      position: new naver.maps.LatLng(pin.latitude, pin.longitude),
      map: map,
      icon: obj[icon],
    });
  });
};
