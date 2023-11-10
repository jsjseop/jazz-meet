import styled from '@emotion/styled';
import { useEffect, useState } from 'react';
import { createPortal } from 'react-dom';
import { useOutletContext } from 'react-router-dom';
import ChevronLeftIcon from '@mui/icons-material/ChevronLeft';
import ChevronRightIcon from '@mui/icons-material/ChevronRight';
import CloseIcon from '@mui/icons-material/Close';

export const ShowDetail: React.FC = () => {
  // const { id: showId } = useParams();
  const mapElement = useOutletContext<React.RefObject<HTMLDivElement>>();
  const [src, setSrc] = useState('');

  useEffect(() => {
    setSrc(
      'https://github.com/jazz-meet/jazz-meet/assets/57666791/7beb1dbf-54cb-407a-9494-9ee45e0bb38d',
    );
  }, []);

  return (
    <>
      {createPortal(
        <StyledShowDetail>
          <StyledShowDetailHeader>
            <CloseIcon
              sx={{ width: '64px', height: '64px', fill: '#B5BEC6' }}
            />
          </StyledShowDetailHeader>
          <StyledShowDetailBody>
            <ChevronLeftIcon
              sx={{ width: '64px', height: '64px', fill: '#B5BEC6' }}
            />
            <StyledShowDetailContent>
              <StyledShowDetailImage>
                <img src={src} alt="poster" />
              </StyledShowDetailImage>
              <StyledShowDetailText>
                올댓재즈는 1976년부터 한국의 재즈씬을 이끌어온 한국의
                대표브랜드로서 재즈 매니아들의 성지와도 같은 공간입니다.
                올댓재즈는 1976년부터 한국의 재즈씬을 이끌어온 한국의
                대표브랜드로서 재즈 매니아들의 성지와도 같은 공간입니다.
                올댓재즈는 1976년부터 한국의 재즈씬을 이끌어온 한국의
                대표브랜드로서 재즈 매니아들의 성지와도 같은 공간입니다.
              </StyledShowDetailText>
            </StyledShowDetailContent>
            <ChevronRightIcon
              sx={{ width: '64px', height: '64px', fill: '#B5BEC6' }}
            />
          </StyledShowDetailBody>
        </StyledShowDetail>,
        mapElement.current ?? document.body,
      )}
    </>
  );
};

const StyledShowDetail = styled.div`
  position: relative;
  z-index: 101;
  width: 100%;
  height: 100%;
  padding: 20px;
  box-sizing: border-box;
  background-color: #00000099;
`;

const StyledShowDetailHeader = styled.div`
  display: flex;
  align-items: center;
`;

const StyledShowDetailBody = styled.div`
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: space-between;
`;

const StyledShowDetailContent = styled.div`
  max-width: 500px;
  height: 100%;
  overflow-y: auto;

  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 20px;
`;

const StyledShowDetailImage = styled.div`
  > img {
    width: 100%;
    object-fit: contain;
  }
`;

const StyledShowDetailText = styled.div`
  width: 100%;
  color: #c6c6c6;
  font-size: 20px;
  line-height: 130%;
  margin-bottom: 100px;
`;
