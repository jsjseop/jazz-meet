import {
  Outlet,
  Route,
  createBrowserRouter,
  createRoutesFromElements,
} from 'react-router-dom';
import { AdminLayout } from './layouts/AdminLayout';
import { BaseLayout } from './layouts/BaseLayout';
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
          <Route path="venues" element={<div>공연장 목록</div>} />
          <Route path="venues/post" element={<div>공연장 생성 및 수정</div>} />
          <Route path="shows" element={<div>공연 목록</div>} />
          <Route path="shows/post" element={<div>공연 생성 및 수정</div>} />
          <Route
            path="inquiries"
            element={<div>문의 목록, 답변, 수정, 삭제</div>}
          />
        </Route>
      </Route>
    </Route>,
  ),
);
