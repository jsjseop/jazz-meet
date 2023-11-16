import { BASIC_COORDINATE } from '~/constants/COORDINATE';
import { PIN_SVG, TIED_EIGHTH_NOTES_SVG } from '~/constants/MAP';
import { Pin } from '~/types/api.types';
import { Coordinate, CoordinateBoundary } from '~/types/map.types';

export const fitBoundsToCoordinates = (
  // pins: Pin[],
  coordinates: Coordinate[],
  map: naver.maps.Map,
) => {
  if (coordinates.length === 0) {
    return;
  }

  if (coordinates.length === 1) {
    map.setCenter(
      new naver.maps.LatLng(coordinates[0].latitude, coordinates[0].longitude),
    );

    return;
  }

  const bounds = new naver.maps.LatLngBounds(
    new naver.maps.LatLng(coordinates[0].latitude, coordinates[0].longitude),
    new naver.maps.LatLng(coordinates[1].latitude, coordinates[1].longitude),
  );

  coordinates.forEach((coord) => {
    bounds.extend(new naver.maps.LatLng(coord.latitude, coord.longitude));
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

const generateMarkerContent = (text: string) =>
  `<div class="marker-container"><div class="marker-icon-container">${TIED_EIGHTH_NOTES_SVG}</div><div class="marker-text">${text}</div></div>`;

export const addMarkersOnMap = (
  pins: Pin[],
  map: naver.maps.Map,
  onMarkerClick: (venueId: number) => void,
) => {
  return pins.map((pin) => {
    const marker = new naver.maps.Marker({
      position: new naver.maps.LatLng(pin.latitude, pin.longitude),
      map: map,
      icon: {
        content: generateMarkerContent(pin.name),
        anchor: new naver.maps.Point(3, 48),
      },
    });

    addMarkerClickEvent(marker, () => onMarkerClick(pin.id));

    return marker;
  });
};

export const addPinsOnMap = (
  pins: Pin[],
  map: naver.maps.Map,
  onPinClick: (venueId: number) => void,
) => {
  return pins.map((pin) => {
    const marker = new naver.maps.Marker({
      position: new naver.maps.LatLng(pin.latitude, pin.longitude),
      map: map,
      icon: {
        content: PIN_SVG,
        anchor: new naver.maps.Point(4.5, 7),
      },
    });

    addMarkerClickEvent(marker, () => onPinClick(pin.id));

    return marker;
  });
};

const addMarkerClickEvent = (
  marker: naver.maps.Marker,
  callBack: () => void,
) => {
  naver.maps.Event.addListener(marker, 'click', callBack);
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

export const getMapBounds = (
  map: naver.maps.Map,
): CoordinateBoundary | undefined => {
  const bounds = map.getBounds();

  if (!(bounds instanceof naver.maps.LatLngBounds)) {
    return;
  }

  return {
    lowLatitude: bounds.south(),
    highLatitude: bounds.north(),
    lowLongitude: bounds.west(),
    highLongitude: bounds.east(),
  };
};
