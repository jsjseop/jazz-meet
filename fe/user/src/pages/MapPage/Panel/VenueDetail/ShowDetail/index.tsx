import styled from '@emotion/styled';
import ChevronLeftIcon from '@mui/icons-material/ChevronLeft';
import ChevronRightIcon from '@mui/icons-material/ChevronRight';
import CloseIcon from '@mui/icons-material/Close';
import { useEffect, useState } from 'react';
import { createPortal } from 'react-dom';
import { useOutletContext } from 'react-router-dom';
import { ShowDetail as ShowDetailType } from '~/types/api.types';

type Props = {
  showDetailInfo: ShowDetailType;
  onCloseClick: () => void;
};

export const ShowDetail: React.FC<Props> = ({
  showDetailInfo,
  onCloseClick,
}) => {
  const mapElement = useOutletContext<React.RefObject<HTMLDivElement>>();
  const [src, setSrc] = useState('');

  useEffect(() => {
    setSrc(showDetailInfo.posterUrl);
  }, []);

  return (
    <>
      {createPortal(
        <StyledShowDetail>
          <StyledShowDetailHeader>
            <CloseIcon
              sx={{ width: '64px', height: '64px', fill: '#B5BEC6' }}
              onClick={onCloseClick}
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
                {showDetailInfo.description}
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
