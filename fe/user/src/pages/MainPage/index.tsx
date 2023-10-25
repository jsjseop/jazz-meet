import { AroundVenus } from './AroundVenus';
import { Banner } from './Banner';
import { OngoingShows } from './OngoingShows';

export const MainPage: React.FC = () => {
  return (
    <div>
      <Banner />
      <AroundVenus />
      <OngoingShows />
    </div>
  );
};
