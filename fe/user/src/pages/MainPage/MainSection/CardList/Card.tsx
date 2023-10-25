import styled from '@emotion/styled';

type Props = {
  poster: {
    id: number;
    thumbnailUrl: string;
    name: string;
    address: string;
  };
};

export const Card: React.FC<Props> = ({ poster }) => {
  const { id, thumbnailUrl, name, address } = poster;

  return (
    <StyledCard key={id}>
      <CardImage src={thumbnailUrl} alt="poster" />
      <Title>{name}</Title>
      <SubTitle>{address}</SubTitle>
    </StyledCard>
  );
};

const StyledCard = styled.div`
  width: 100%;
  box-sizing: border-box;
  margin-bottom: 37px;
`;

const CardImage = styled.img`
  width: 100%;
  height: 380px;
  object-fit: cover;
  margin-bottom: 24px;
`;

const Title = styled.div`
  margin-bottom: 5px;
`;

const SubTitle = styled.div``;
