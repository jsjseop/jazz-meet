import { create } from 'zustand';
import { DeviceType } from '~/types/device.types';

type DeviceTypeState = {
  deviceType: DeviceType;
  updateDeviceType: () => void;
};

const MOBILE_MAX_WIDTH = 1023;

export const useDeviceTypeStore = create<DeviceTypeState>((set) => ({
  deviceType: {
    isMobile: false,
  },
  updateDeviceType: () =>
    set((state) => {
      const { innerWidth } = window;
      const isMobile = innerWidth <= MOBILE_MAX_WIDTH;

      return { deviceType: { ...state.deviceType, isMobile } };
    }),
}));
