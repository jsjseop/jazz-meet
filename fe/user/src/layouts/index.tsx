import { useDeviceTypeStore } from '~/stores/useDeviceTypeStore';
import { MobileLayout } from './MobileLayout';
import { PCLayout } from './PCLayout';

export const Layout: React.FC = () => {
  const isMobile = useDeviceTypeStore((state) => state.isMobile);

  return <>{isMobile ? <MobileLayout /> : <PCLayout />}</>;
};
