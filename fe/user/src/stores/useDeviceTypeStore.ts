import { create } from 'zustand';
import { DeviceType } from '~/types/device.types';

type DeviceTypeState = {
  deviceType: DeviceType;
  setDeviceType: (deviceType: DeviceType) => void;
};

export const useDeviceTypeStore = create<DeviceTypeState>((set) => ({
  deviceType: {
    isMobile: false,
  },
  setDeviceType: (deviceType: DeviceType) => set(() => ({ deviceType })),
}));
