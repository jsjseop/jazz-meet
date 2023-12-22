import {
  Route,
  createBrowserRouter,
  createRoutesFromElements,
} from 'react-router-dom';
import { Layout } from './layouts';
import { InquiryPage } from './pages/InquiryPage';
import { MainPage } from './pages/MainPage';
import { MapPage } from './pages/MapPage';
import { VenueDetail } from './pages/MapPage/Panel/VenueDetail';
import { ShowPage } from './pages/ShowPage';

export const router = createBrowserRouter(
  createRoutesFromElements(
    <Route path="/">
      <Route element={<Layout />}>
        <Route index element={<MainPage />} />
        <Route path="map" element={<MapPage />}>
          <Route path="venues/:venueId" element={<VenueDetail />} />
        </Route>
        <Route path="show" element={<ShowPage />} />
        <Route path="inquiry" element={<InquiryPage />} />
      </Route>
    </Route>,
  ),
);
