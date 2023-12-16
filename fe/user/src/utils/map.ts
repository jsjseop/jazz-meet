import { BASIC_COORDINATE, PIN_SVG } from '~/constants/MAP';
import { Coordinate, CoordinateBoundary, Pin } from '~/types/map.types';

export const panToCoordinates = (
  coordinates: Coordinate[],
  map: naver.maps.Map,
) => {
  if (coordinates.length === 0) {
    return;
  }

  if (coordinates.length === 1) {
    map.panTo(
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

  map.panToBounds(bounds);
};

export const fitBoundsToBoundary = (
  coordinates: CoordinateBoundary,
  map: naver.maps.Map,
) => {
  const bounds = new naver.maps.LatLngBounds(
    new naver.maps.LatLng(coordinates.lowLatitude, coordinates.lowLongitude),
    new naver.maps.LatLng(coordinates.highLatitude, coordinates.highLongitude),
  );

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

  map.panToBounds(bounds);
};

const generateTiedEighthNotesSVG = (isActive?: boolean) =>
  `<svg width="22" height="22" viewBox="0 0 22 22" fill="none" xmlns="http://www.w3.org/2000/svg"><path d="M17.755 2.86329C17.6792 2.80317 17.5905 2.7614 17.4958 2.74126C17.4011 2.72111 17.3031 2.72313 17.2094 2.74716L7.02754 5.29261C6.89 5.32699 6.76788 5.40631 6.68055 5.51799C6.59322 5.62967 6.54569 5.76732 6.54549 5.90909V14.667C6.01457 14.3123 5.38033 14.1452 4.74355 14.1922C4.10676 14.2393 3.50397 14.4978 3.03096 14.9268C2.55796 15.3557 2.24187 15.9304 2.13293 16.5595C2.02399 17.1887 2.12846 17.8362 2.42972 18.3992C2.73098 18.9622 3.21176 19.4083 3.79565 19.6667C4.37954 19.9252 5.03305 19.981 5.65233 19.8254C6.2716 19.6698 6.82112 19.3118 7.21355 18.8081C7.60598 18.3044 7.81881 17.684 7.81822 17.0455V9.58806L16.7273 7.36079V12.1216C16.1964 11.7669 15.5621 11.5997 14.9254 11.6468C14.2886 11.6938 13.6858 11.9524 13.2128 12.3813C12.7398 12.8102 12.4237 13.3849 12.3147 14.0141C12.2058 14.6432 12.3103 15.2908 12.6115 15.8537C12.9128 16.4167 13.3936 16.8629 13.9775 17.1213C14.5614 17.3797 15.2149 17.4356 15.8341 17.28C16.4534 17.1244 17.0029 16.7663 17.3954 16.2626C17.7878 15.7589 18.0006 15.1385 18 14.5V3.36363C17.9998 3.26712 17.9776 3.17191 17.9352 3.08523C17.8928 2.99854 17.8311 2.92265 17.755 2.86329Z" fill="${
    isActive ? '#1B1B1B' : '#ffffff'
  }"/></svg>`;

const generateMarkerContent = (text: string, isActive?: boolean) =>
  `<div class="marker${
    isActive ? ' active' : ''
  }"><div class="marker--icon">${generateTiedEighthNotesSVG(
    isActive,
  )}</div><div class="marker--text">${text}</div></div>`;

export const addMarkersOnMap = ({
  pins,
  map,
  selectedVenueId,
  onMarkerClick,
}: {
  pins: Pin[];
  map: naver.maps.Map;
  selectedVenueId: number;
  onMarkerClick: (venueId: number) => void;
}) => {
  return pins.map((pin) => {
    const marker = new naver.maps.Marker({
      position: new naver.maps.LatLng(pin.latitude, pin.longitude),
      map: map,
      icon: {
        content: generateMarkerContent(pin.name, selectedVenueId === pin.id),
        anchor: new naver.maps.Point(0, 50),
      },
    });

    addMarkerClickEvent(marker, () => onMarkerClick(pin.id));

    return marker;
  });
};

export const addPinsOnMap = ({
  pins,
  map,
  selectedVenueId,
  onPinClick,
}: {
  pins: Pin[];
  map: naver.maps.Map;
  selectedVenueId: number;
  onPinClick: (venueId: number) => void;
}) => {
  return pins.map((pin) => {
    const marker = new naver.maps.Marker({
      position: new naver.maps.LatLng(pin.latitude, pin.longitude),
      map: map,
      icon:
        selectedVenueId === pin.id
          ? {
              content: generateMarkerContent(pin.name, true),
              anchor: new naver.maps.Point(0, 50),
            }
          : {
              content: PIN_SVG,
              anchor: new naver.maps.Point(0, 32),
            },
    });

    addMarkerClickEvent(marker, () => onPinClick(pin.id));

    addMarkerMouseOverEvent(marker, () => {
      marker.setIcon({
        content: generateMarkerContent(pin.name, selectedVenueId === pin.id),
        anchor: new naver.maps.Point(0, 50),
      });
    });

    addMarkerMouseOutEvent(marker, () => {
      marker.setIcon(
        selectedVenueId === pin.id
          ? {
              content: generateMarkerContent(pin.name, true),
              anchor: new naver.maps.Point(0, 50),
            }
          : {
              content: PIN_SVG,
              anchor: new naver.maps.Point(0, 32),
            },
      );
    });

    return marker;
  });
};

const addMarkerClickEvent = (
  marker: naver.maps.Marker,
  callBack: () => void,
) => {
  naver.maps.Event.addListener(marker, 'click', callBack);
};

const addMarkerMouseOverEvent = (
  marker: naver.maps.Marker,
  callBack: () => void,
) => {
  naver.maps.Event.addListener(marker, 'mouseover', callBack);
};

const addMarkerMouseOutEvent = (
  marker: naver.maps.Marker,
  callBack: () => void,
) => {
  naver.maps.Event.addListener(marker, 'mouseout', callBack);
};

export const getInitMap = () => {
  return new naver.maps.Map('map', {
    center: new naver.maps.LatLng(
      BASIC_COORDINATE.latitude,
      BASIC_COORDINATE.longitude,
    ),
    mapDataControl: false,
    tileTransition: false,
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

export const getQueryBounds = (
  query: string,
): CoordinateBoundary | undefined => {
  const urlParams = new URLSearchParams(query);

  const lowLatitude = urlParams.get('lowLatitude');
  const highLatitude = urlParams.get('highLatitude');
  const lowLongitude = urlParams.get('lowLongitude');
  const highLongitude = urlParams.get('highLongitude');

  if (!lowLatitude || !highLatitude || !lowLongitude || !highLongitude) {
    return;
  }

  return {
    lowLatitude: Number(lowLatitude),
    highLatitude: Number(highLatitude),
    lowLongitude: Number(lowLongitude),
    highLongitude: Number(highLongitude),
  };
};

export const isSameCoordinate = (
  coord1: CoordinateBoundary,
  coord2: CoordinateBoundary,
) => {
  return (
    coord1.lowLatitude === coord2.lowLatitude &&
    coord1.highLatitude === coord2.highLatitude &&
    coord1.lowLongitude === coord2.lowLongitude &&
    coord1.highLongitude === coord2.highLongitude
  );
};

export const addMapButton = ({
  map,
  buttonHTMLString,
  position,
  onClick,
}: {
  map: naver.maps.Map;
  buttonHTMLString: string;
  position: naver.maps.Position;
  onClick: () => void;
}) => {
  const customControl = new naver.maps.CustomControl(buttonHTMLString, {
    position,
  });

  naver.maps.Event.once(map, 'init', () => {
    customControl.setMap(map);

    naver.maps.Event.addDOMListener(
      customControl.getElement(),
      'click',
      onClick,
    );
  });
};
