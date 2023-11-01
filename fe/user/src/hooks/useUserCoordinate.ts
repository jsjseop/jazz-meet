import { useEffect } from 'react';
import { useShallow } from 'zustand/react/shallow';
import { useUserCoordinateStore } from '~/stores/useUserCoordinateStore';

export const useUserCoordinate = () => {
  const { userCoordinate, setUserCoordinate } = useUserCoordinateStore(
    useShallow(({ userCoordinate, setUserCoordinate }) => ({
      userCoordinate,
      setUserCoordinate,
    })),
  );

  useEffect(() => {
    if (userCoordinate) {
      return;
    }

    navigator.geolocation.getCurrentPosition((position) => {
      setUserCoordinate({
        latitude: position.coords.latitude,
        longitude: position.coords.longitude,
      });
    });
  }, [userCoordinate, setUserCoordinate]);

  return { userCoordinate };
};
