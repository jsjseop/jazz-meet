import {
  Outlet,
  Route,
  createBrowserRouter,
  createRoutesFromElements,
} from 'react-router-dom';
import { AdminLayout } from './layouts/AdminLayout';
import { BaseLayout } from './layouts/BaseLayout';
import { InquiriesPage } from './pages/AdminPage/InquiriesPage';
import { ShowsPage } from './pages/AdminPage/ShowsPage';
import { VenuesPage } from './pages/AdminPage/VenuesPage';
import { InquiryPage } from './pages/InquiryPage';
import { MainPage } from './pages/MainPage';
import { MapPage } from './pages/MapPage';
import { VenueDetail } from './pages/MapPage/Panel/VenueDetail';
import { ShowDetail } from './pages/MapPage/Panel/VenueDetail/ShowDetail';
import { ShowPage } from './pages/ShowPage';

export const router = createBrowserRouter(
  createRoutesFromElements(
    <Route path="/">
      <Route element={<BaseLayout />}>
        <Route index element={<MainPage />} />
        <Route path="map" element={<MapPage />}>
          <Route path="venues/:venueId" element={<VenueDetail />}>
            <Route path="shows/:showId" element={<ShowDetail />} />
          </Route>
        </Route>
        <Route path="show" element={<ShowPage />} />
        <Route path="inquiry" element={<InquiryPage />} />
      </Route>
      <Route element={<AdminLayout />}>
        <Route path="admin" element={<Outlet />}>
          <Route path="venues" element={<VenuesPage />} />
          <Route path="venues/post" element={<div>공연장 생성 및 수정</div>} />
          <Route path="shows" element={<ShowsPage />} />
          <Route path="shows/post" element={<div>공연 생성 및 수정</div>} />
          <Route path="inquiries" element={<InquiriesPage />} />
        </Route>
      </Route>
    </Route>,
  ),
);
