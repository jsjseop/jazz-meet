import styled from '@emotion/styled';
import { useEffect } from 'react';
import { Outlet, useLocation, useNavigate } from 'react-router-dom';
import { useShallow } from 'zustand/react/shallow';
import { VenueListData } from '~/hooks/useVenueList';
import { usePathHistoryStore } from '~/stores/usePathHistoryStore';
import { VenueList } from './VenueList';

type Props = {
  mapRef: React.RefObject<HTMLDivElement>;
} & VenueListData;

export const Panel: React.FC<Props> = ({ mapRef, ...venueListData }) => {
  const navigate = useNavigate();
  const { search } = useLocation();
  const { previousPath, setPreviousPath } = usePathHistoryStore(
    useShallow((state) => ({
      previousPath: state.previousPath,
      setPreviousPath: state.setPreviousPath,
    })),
  );

  useEffect(() => {
    const venueId = new URLSearchParams(search).get('venueId');
    const detailPath = `/map/venues/${venueId}`;

    // 자동 이동 방지
    if (venueId && previousPath !== detailPath) {
      navigate(detailPath);
    }
  }, [search, navigate, previousPath]);

  useEffect(() => {
    return () => setPreviousPath('');
  }, []);

  return (
    <StyledPanel>
      <VenueList {...venueListData} />
      <Outlet context={mapRef} />
    </StyledPanel>
  );
};

const StyledPanel = styled.div`
  position: relative;
  width: 100%;
  height: 100%;
  display: flex;
`;
