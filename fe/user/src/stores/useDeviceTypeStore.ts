import { create } from 'zustand';

type DeviceTypeState = {
  isMobile: boolean;

  updateDeviceType: () => void;
};

export const useDeviceTypeStore = create<DeviceTypeState>((set) => ({
  isMobile: false,

  updateDeviceType: () =>
    set(() => {
      const mobileWidthInRange = { max: 1023 };

      return {
        isMobile: isWidthInRange(mobileWidthInRange),
      };
    }),
}));

const isWidthInRange = ({ min, max }: { min?: number; max?: number }) => {
  const windowWidth = window.innerWidth;

  if (min !== undefined && max !== undefined) {
    return windowWidth >= min && windowWidth <= max;
  }

  if (min !== undefined) {
    return windowWidth >= min;
  }

  if (max !== undefined) {
    return windowWidth <= max;
  }

  return false;
};
