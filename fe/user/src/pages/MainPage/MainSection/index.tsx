import styled from '@emotion/styled';
import { AroundVenus } from './AroundVenus';
import { UpcomingShows } from './UpcomingShows';

export const MainSection: React.FC = () => {
  return (
    <StyledMainSection>
      <AroundVenus />
      <UpcomingShows />
    </StyledMainSection>
  );
};

const StyledMainSection = styled.section`
  max-width: 1200px;
  margin: 0 auto 173px auto;
  display: flex;
  flex-direction: column;
  gap: 60px;
`;
