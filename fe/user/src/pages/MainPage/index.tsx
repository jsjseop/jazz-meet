import styled from '@emotion/styled';
import { AroundVenus } from './AroundVenus';
import { Banner } from './Banner';
import { OngoingShows } from './OngoingShows';

export const MainPage: React.FC = () => {
  return (
    <>
      <Banner />
      <MainSection>
        <AroundVenus />
        <OngoingShows />
      </MainSection>
    </>
  );
};

const MainSection = styled.div`
  max-width: 1200px;
  margin: 0 auto;
`;
