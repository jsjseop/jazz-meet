import styled from '@emotion/styled';
import { AroundVenus } from './AroundVenus';
import { Banner } from './Banner';
import { OngoingShows } from './OngoingShows';
import { Footer } from './Footer';

export const MainPage: React.FC = () => {
  return (
    <>
      <Banner />
      <MainSection>
        <AroundVenus />
        <OngoingShows />
      </MainSection>
      <Footer />
    </>
  );
};

const MainSection = styled.div`
  max-width: 1200px;
  margin: 0 auto 173px auto;
`;
