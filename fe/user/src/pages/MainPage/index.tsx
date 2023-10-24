import { AroundVenus } from './AroundVenus';
import { Carousel } from './Carousel';
import { OngoingShows } from './OngoingShows';

export const MainPage: React.FC = () => {
  return (
    <div>
      <Carousel />
      <AroundVenus />
      <OngoingShows />
    </div>
  );
};
