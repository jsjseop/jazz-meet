import { BASIC_COORDINATE } from '~/constants/COORDINATE';
import { MARKER_SVG } from '~/constants/MAP';
import { Pin } from '~/types/api.types';
import { Coordinate } from '~/types/map.types';

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

export const fitBoundsToCoordinateBoundary = (
  searchQueryString: string,
  map: naver.maps.Map,
) => {
  const urlParams = new URLSearchParams(searchQueryString);

  const lowLatitude = urlParams.get('lowLatitude');
  const highLatitude = urlParams.get('highLatitude');
  const lowLongitude = urlParams.get('lowLongitude');
  const highLongitude = urlParams.get('highLongitude');

  const bounds = new naver.maps.LatLngBounds(
    new naver.maps.LatLng(Number(lowLatitude), Number(lowLongitude)),
    new naver.maps.LatLng(Number(highLatitude), Number(highLongitude)),
  );

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
      // content: MARKER_SVG2,
      // anchor: new naver.maps.Point(4.5, 7),
      content: MARKER_SVG,
      anchor: new naver.maps.Point(3, 48),
    },
  };

  return pins.map(
    (pin) =>
      new naver.maps.Marker({
        position: new naver.maps.LatLng(pin.latitude, pin.longitude),
        map: map,
        icon: obj[icon],
      }),
  );
};

export const getInitMap = (userCoordinate: Coordinate | null) => {
  const initCoordinate = userCoordinate ?? BASIC_COORDINATE;

  return new naver.maps.Map('map', {
    center: new naver.maps.LatLng(
      initCoordinate.latitude,
      initCoordinate.longitude,
    ),
  });
};
