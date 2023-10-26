import styled from '@emotion/styled';
import { SwiperComponent } from './SwiperComponent';

export const Banner: React.FC = () => {
  return (
    <StyledBanner>
      <SwiperComponent
        imageUrls={[
          'https://github.com/jsh3418/js-calculator-bonobono/assets/57666791/85b44f33-49b9-46d6-80de-e9a538c44b2f',
          'https://github.com/jsh3418/js-calculator-bonobono/assets/57666791/dc2bf706-ec24-4e5b-b741-467f91ae8618',
          'https://github.com/jsh3418/js-calculator-bonobono/assets/57666791/f51d660d-a2ba-4ca9-9dc3-84214eb37ae5',
          'https://github.com/jsh3418/js-calculator-bonobono/assets/57666791/51a4d669-a2e7-4a90-b4d4-af4e9c63dd0d',
          'https://github.com/jsh3418/js-calculator-bonobono/assets/57666791/0ee2e38f-0e63-498d-963d-8f1abe5239a0'
        ]}
      />
    </StyledBanner>
  );
};

const StyledBanner = styled.div`
  margin-bottom: 105px;
`;
