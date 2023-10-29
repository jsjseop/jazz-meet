import styled from '@emotion/styled';
import CloseIcon from '@mui/icons-material/Close';
import ChevronLeftIcon from '@mui/icons-material/ChevronLeft';
import ChevronRightIcon from '@mui/icons-material/ChevronRight';

export const ImageDetail: React.FC = () => {
  return (
    <StyledImageDetail>
      <StyledImageDetailHeader>
        <CloseIcon sx={{ width: '64px', height: '64px', fill: '#B5BEC6' }} />
      </StyledImageDetailHeader>
      <StyledImageDetailContent>
        <ChevronLeftIcon
          sx={{ width: '64px', height: '64px', fill: '#B5BEC6' }}
        />

        <StyledImageWrapper>
          <img
            src="https://github.com/jazz-meet/jazz-meet/assets/57666791/352473b5-94e8-4234-8645-ea69d6c9b1c1"
            alt="poster"
          />
        </StyledImageWrapper>

        <ChevronRightIcon
          sx={{ width: '64px', height: '64px', fill: '#B5BEC6' }}
        />
      </StyledImageDetailContent>
    </StyledImageDetail>
  );
};

const StyledImageDetail = styled.div`
  position: fixed;
  z-index: 101;
  top: 73px;
  width: 100vw;
  height: 100vh;
  background-color: #00000099;
`;

const StyledImageDetailHeader = styled.div`
  position: absolute;
  top: 20px;
  left: 20px;
`;

const StyledImageDetailContent = styled.div`
  height: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 100px;
  box-sizing: border-box;
`;

const StyledImageWrapper = styled.div`
  width: 100%;
  height: 100%;

  img {
    width: 100%;
    height: 100%;
    object-fit: contain;
  }
`;
