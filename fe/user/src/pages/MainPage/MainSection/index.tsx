import styled from '@emotion/styled';
import { AroundVenus } from './AroundVenus';
import { OngoingShows } from './OngoingShows';

export const MainSection: React.FC = () => {
  return (
    <StyledMainSection>
      <AroundVenus />
      <OngoingShows />
    </StyledMainSection>
  );
};

const StyledMainSection = styled.section`
  max-width: 1200px;
  margin: 0 auto 173px auto;
`;
