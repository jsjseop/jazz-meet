import { useEffect, useState } from 'react';
import { Coordinate } from 'types/map.types';

export const useUserCoordinate = () => {
  const [userCoordinate, setUserCoordinate] = useState<Coordinate>();

  useEffect(() => {
    navigator.geolocation.getCurrentPosition((position) => {
      setUserCoordinate({
        latitude: position.coords.latitude,
        longitude: position.coords.longitude,
      });
    });
  }, []);

  return { userCoordinate };
};
