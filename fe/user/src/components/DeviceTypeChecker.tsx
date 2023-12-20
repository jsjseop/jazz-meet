import { useEffect } from 'react';
import { useDeviceTypeStore } from '~/stores/useDeviceTypeStore';

export const DeviceTypeChecker: React.FC = () => {
  const updateDeviceType = useDeviceTypeStore(
    (state) => state.updateDeviceType,
  );

  useEffect(() => {
    updateDeviceType();
    window.addEventListener('resize', updateDeviceType);

    return () => {
      window.removeEventListener('resize', updateDeviceType);
    };
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  return null;
};
