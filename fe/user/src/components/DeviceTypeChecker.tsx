import { useEffect } from 'react';
import { useDeviceTypeStore } from '~/stores/useDeviceTypeStore';

const MOBILE_MAX_WIDTH = 1023;

export const DeviceTypeChecker: React.FC = () => {
  const { setDeviceType } = useDeviceTypeStore();

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
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  return null;
};
