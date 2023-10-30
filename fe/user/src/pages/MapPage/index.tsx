import styled from '@emotion/styled';
import { Map } from './Map';
import { Panel } from './Panel';

export const MapPage: React.FC = () => {
  return (
    <StyledMapPage>
      <Map />
      <Panel />
    </StyledMapPage>
  );
};

const StyledMapPage = styled.div`
  height: calc(100vh - 73px);
  display: flex;
`;
