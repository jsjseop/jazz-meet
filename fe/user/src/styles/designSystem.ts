import { css, keyframes } from '@emotion/react';

const colors = {
  white: '#FFFFFF',
  grey50: '#FAFAFA',
  grey100: `#F9F9F9`,
  grey200: `#F5F5F5`,
  grey300: `#B3B3B3`,
  grey400: `#EFEFF0`,
  grey500: `#B3B3B3`,
  grey600: `#000000`,
  grey700: `#3C3C43`,
  grey800: `#3C3C43`,
  grey900: '#3C3C43',
  black: '#000000',
  mint: '#00C7BE',
  yellow: '#FFD43B',
  yellowStrong: '#FFB800',
  orange: '#FF9500',
  red: '#FF3B30',
};

const fonts = {
  displayStrong32: '700 32px Noto Sans KR, sans-serif',
  displayStrong20: '700 20px Noto Sans KR, sans-serif',
  displayStrong16: '700 16px Noto Sans KR, sans-serif',
  displayDefault16: '400 16px Noto Sans KR, sans-serif',
  displayDefault12: '400 12px Noto Sans KR, sans-serif',

  availableStrong16: '700 16px Noto Sans KR, sans-serif',
  availableStrong12: '700 12px Noto Sans KR, sans-serif',
  availableStrong10: '700 10px Noto Sans KR, sans-serif',
  availableDefault16: '400 16px Noto Sans KR, sans-serif',
  availableDefault12: '400 12px Noto Sans KR, sans-serif',

  enabledStrong16: '700 16px Noto Sans KR, sans-serif',
  enabledStrong12: '700 12px Noto Sans KR, sans-serif',
};

export const designSystem = {
  color: {
    neutral: {
      text: colors.grey900,
      textWeak: colors.grey800,
      textStrong: colors.black,
      background: colors.white,
      backgroundWeak: colors.grey50,
      backgroundBold: colors.grey400,
      backgroundBlur: colors.grey100, // needs backdrop-filter: blur(8px);
      border: colors.grey500,
      borderStrong: colors.grey700,
      overlay: colors.grey600,
    },
    accent: {
      text: colors.white,
      textWeak: colors.black,
      backgroundPrimary: colors.orange,
      backgroundSecondary: colors.mint,
    },
    system: {
      warning: colors.red,
      background: colors.white,
      backgroundWeak: colors.grey200,
    },
    brand: {
      primary: colors.yellow,
      primaryStrong: colors.yellowStrong,
    },
  },
  filter: {
    neutralTextWeak:
      'brightness(0) saturate(100%) invert(15%) sepia(2%) saturate(5010%) hue-rotate(202deg) brightness(93%) contrast(73%)',
    accentText:
      'invert(100%) sepia(97%) saturate(15%) hue-rotate(110deg) brightness(103%) contrast(102%)',
  },
  backdropFilter: {
    blur: 'blur(8px)',
  },
  font: fonts,
};

export const clickableStyle = css`
  &:hover {
    cursor: pointer;
    opacity: 0.7;
  }

  &:active {
    opacity: 0.5;
  }
`;

const skeletonColorChange = keyframes`
  0% {
    background-color: #DBE1E4;
  }
  100% {
    background-color: #A3A4A9;
  }
`;

export const paintSkeleton = css`
  animation: ${skeletonColorChange} 1s ease-in-out infinite alternate;
`;
