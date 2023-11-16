import { Coordinate } from 'types/map.types';

export const BASIC_COORDINATE: Coordinate = {
  latitude: 37.5666103,
  longitude: 126.9783882,
}; // 서울시청 좌표

export const PIN_SVG = `<svg width="36" height="36" viewBox="0 0 54 54" fill="none" xmlns="http://www.w3.org/2000/svg"><g filter="url(#filter0_d_831_6568)"><path fill-rule="evenodd" clip-rule="evenodd" d="M27 48C40.2548 48 51 37.2548 51 24C51 10.7452 40.2548 0 27 0C13.7452 0 3 10.7452 3 24V46.5455C3 47.3488 3.65122 48 4.45455 48H27Z" fill="#ffffff"/></g><circle cx="27" cy="24" r="18.9091" fill="#47484e"/><path d="M33.5466 18.2998L27.183 16.3907C27.0879 16.3622 26.9876 16.3563 26.8899 16.3734C26.7922 16.3906 26.6998 16.4304 26.6203 16.4896C26.5407 16.5488 26.476 16.6257 26.4314 16.7144C26.3869 16.803 26.3637 16.9008 26.3636 17V25.6116C25.7119 25.0287 24.8799 24.6871 24.0066 24.644C23.1333 24.6008 22.2717 24.8587 21.5657 25.3746C20.8597 25.8904 20.3521 26.6329 20.1278 27.478C19.9035 28.3231 19.976 29.2196 20.3333 30.0176C20.6905 30.8157 21.3109 31.4669 22.0906 31.8625C22.8704 32.2581 23.7623 32.3741 24.6173 32.1911C25.4723 32.0081 26.2386 31.5372 26.7881 30.8571C27.3376 30.177 27.637 29.3289 27.6364 28.4546V21.6733L33.1807 23.3366C33.2757 23.3651 33.3761 23.371 33.4738 23.3539C33.5715 23.3367 33.6638 23.2969 33.7434 23.2377C33.823 23.1785 33.8877 23.1016 33.9322 23.0129C33.9768 22.9243 34 22.8265 34 22.7273V18.9091C34 18.7723 33.9558 18.6391 33.8742 18.5294C33.7925 18.4196 33.6776 18.3391 33.5466 18.2998Z" fill="white"/><defs><filter id="filter0_d_831_6568" x="0.0909083" y="0" width="53.8182" height="53.8182" filterUnits="userSpaceOnUse" color-interpolation-filters="sRGB"><feFlood flood-opacity="0" result="BackgroundImageFix"/><feColorMatrix in="SourceAlpha" type="matrix" values="0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 127 0" result="hardAlpha"/><feOffset dy="2.90909"/><feGaussianBlur stdDeviation="1.45455"/><feComposite in2="hardAlpha" operator="out"/><feColorMatrix type="matrix" values="0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0.25 0"/><feBlend mode="normal" in2="BackgroundImageFix" result="effect1_dropShadow_831_6568"/><feBlend mode="normal" in="SourceGraphic" in2="effect1_dropShadow_831_6568" result="shape"/></filter></defs></svg>`;

const SCOPE_Z_INDEX = 100;
const Z_INDEX_GAP = 100;
export const MARKER_Z_INDEX = SCOPE_Z_INDEX + Z_INDEX_GAP; // 200
export const SELECTED_MARKER_Z_INDEX = MARKER_Z_INDEX + Z_INDEX_GAP; // 300
export const HOVER_MARKER_Z_INDEX = SELECTED_MARKER_Z_INDEX + Z_INDEX_GAP; // 400
