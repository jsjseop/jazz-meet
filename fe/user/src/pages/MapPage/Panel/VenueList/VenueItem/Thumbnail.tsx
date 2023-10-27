import styled from '@emotion/styled';

export const Thumbnail: React.FC = () => {
  return (
    <StyledWrapper>
      <StyledThumbnail
        src="https://search.pstatic.net/common/?src=https%3A%2F%2Fldb-phinf.pstatic.net%2F20221111_235%2F1668155402284iEk7x_JPEG%2FKakaoTalk_20221111_172729297.jpg"
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
