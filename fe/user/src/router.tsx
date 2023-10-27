import { BaseLayout } from 'layouts/BaseLayout';
import { MainPage } from '@pages/MainPage';
import { MapPage } from '@pages/MapPage';
import { InquiryPage } from '@pages/InquiryPage';
import {
  Route,
  createBrowserRouter,
  createRoutesFromElements,
} from 'react-router-dom';

export const router = createBrowserRouter(
  createRoutesFromElements(
    <Route path="/">
      <Route element={<BaseLayout />}>
        <Route index element={<MainPage />} />
        <Route path="map" element={<MapPage />}>
          <Route path=":id" element={<MapPage />} />
        </Route>
        <Route path="inquiry" element={<InquiryPage />} />
      </Route>
    </Route>,
  ),
);
