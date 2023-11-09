import styled from '@emotion/styled';

type Props = {
  url: string;
}

export const Thumbnail: React.FC<Props> = ({
  url
}) => {
  return (
    <StyledWrapper>
      <StyledThumbnail
        src={url}
        alt=""
      />
    </StyledWrapper>
  );
};

const StyledWrapper = styled.div`
  position: relative;

  width: 256px;
  height: 256px;
`;

const StyledThumbnail = styled.img`
  width: 100%;
  height: 100%;
  object-fit: cover;
  border-radius: 10px;
`;
