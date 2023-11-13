import styled from '@emotion/styled';
import { CalendarDate } from './CalendarDate';
import { ShowContainer } from './ShowContainer';

export const ShowPage: React.FC = () => {
  return (
    <StyledShowPage>
      <CalendarDate />
      <ShowContainer />
    </StyledShowPage>
  );
};

const StyledShowPage = styled.div`
  max-width: 1200px;
  margin: 80px auto 140px;
`;
