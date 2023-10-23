import '@emotion/react';
import { designSystem } from '@styles/designSystem';

type ThemeType = typeof designSystem;

declare module '@emotion/react' {
  export interface Theme extends ThemeType {}
}
