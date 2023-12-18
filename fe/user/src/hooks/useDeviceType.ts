import { useEffect, useState } from 'react';

const MOBILE_MAX_WIDTH = 1023;

type DeviceType = {
  isMobile: boolean;
};

export const useDeviceType = () => {
  const [deviceType, setDeviceType] = useState<DeviceType>({
    isMobile: false,
  });

  useEffect(() => {
    const handleResize = () => {
      const { innerWidth } = window;
      const isMobile = innerWidth <= MOBILE_MAX_WIDTH;

      setDeviceType({ isMobile });
    };

    window.addEventListener('resize', handleResize);

    return () => {
      window.removeEventListener('resize', handleResize);
    };
  }, []);

  return deviceType;
};
