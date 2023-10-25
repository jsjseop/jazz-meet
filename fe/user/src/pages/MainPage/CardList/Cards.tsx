import styled from '@emotion/styled';
import { Card } from './Card';

type Props = {
  posters: {
    id: number;
    thumbnailUrl: string;
    name: string;
    address: string;
  }[];
};

export const Cards: React.FC<Props> = ({ posters }) => {
  return (
    <StyledCards>
      {posters.map((poster) => (
        <Card key={poster.id} poster={poster} />
      ))}
    </StyledCards>
  );
};

const StyledCards = styled.ul`
  margin-bottom: 37px;
  display: flex;
  gap: 24px;
`;
