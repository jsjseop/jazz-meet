import styled from '@emotion/styled';
import { SwiperComponent } from './SwiperComponent';

export const Banner: React.FC = () => {
  return (
    <StyledBanner>
      <SwiperComponent
        imageUrls={[
          'https://github.com/jsh3418/js-calculator-bonobono/assets/57666791/85b44f33-49b9-46d6-80de-e9a538c44b2f',
          'https://github.com/jsh3418/js-calculator-bonobono/assets/57666791/85b44f33-49b9-46d6-80de-e9a538c44b2f',
          'https://github.com/jsh3418/js-calculator-bonobono/assets/57666791/85b44f33-49b9-46d6-80de-e9a538c44b2f',
        ]}
      />
    </StyledBanner>
  );
};

const StyledBanner = styled.div`
  margin-bottom: 105px;
`;
