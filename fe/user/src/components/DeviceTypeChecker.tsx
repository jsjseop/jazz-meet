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
  }, [updateDeviceType]);

  return null;
};
