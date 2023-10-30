import styled from '@emotion/styled';
import { Map } from './Map';
import { Panel } from './Panel';
import { useRef } from 'react';

export const MapPage: React.FC = () => {
  const mapRef = useRef<HTMLDivElement>(null);

  return (
    <StyledMapPage>
      <Map mapRef={mapRef} />
      <Panel mapRef={mapRef} />
    </StyledMapPage>
  );
};

const StyledMapPage = styled.div`
  height: calc(100vh - 73px);
  display: flex;
`;
