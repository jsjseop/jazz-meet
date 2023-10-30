import { BaseLayout } from 'layouts/BaseLayout';
import { MainPage } from '@pages/MainPage';
import { MapPage } from '@pages/MapPage';
import { InquiryPage } from '@pages/InquiryPage';
import {
  Route,
  createBrowserRouter,
  createRoutesFromElements,
} from 'react-router-dom';
import { VenueDetail } from '@pages/MapPage/Panel/VenueDetail';
import { ShowDetail } from '@pages/MapPage/Panel/VenueDetail/ShowDetail';

export const router = createBrowserRouter(
  createRoutesFromElements(
    <Route path="/">
      <Route element={<BaseLayout />}>
        <Route index element={<MainPage />} />
        <Route path="map" element={<MapPage />}>
          <Route path="venues/:id" element={<VenueDetail />}>
            <Route path="shows/:id" element={<ShowDetail />} />
          </Route>
        </Route>
        <Route path="inquiry" element={<InquiryPage />} />
      </Route>
    </Route>,
  ),
);
