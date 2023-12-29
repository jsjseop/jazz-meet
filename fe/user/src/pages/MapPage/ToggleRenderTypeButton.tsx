import styled from '@emotion/styled';
import ListAltIcon from '@mui/icons-material/ListAlt';
import MapOutlinedIcon from '@mui/icons-material/MapOutlined';
import { BOTTOM_NAVIGATION_Z_INDEX } from '~/constants/Z_INDEX';
import { RenderType } from '~/types/device.types';

type Props = {
  renderType: RenderType;
  changeRenderType: (type: RenderType) => void;
};

export const ToggleRenderTypeButton: React.FC<Props> = ({
  renderType,
  changeRenderType,
}) => {
  return (
    <>
      {renderType === 'map' ? (
        <StyledRenderToggleButton onClick={() => changeRenderType('list')}>
          <div>리스트 보기</div>
          <ListAltIcon />
        </StyledRenderToggleButton>
      ) : (
        <StyledRenderToggleButton onClick={() => changeRenderType('map')}>
          <div>지도 보기</div>
          <MapOutlinedIcon />
        </StyledRenderToggleButton>
      )}
    </>
  );
};

const StyledRenderToggleButton = styled.div`
  border-radius: 5px;
  background-color: #47484e;
  border: 1px solid #dbdbdb;
  padding: 10px;
  box-sizing: border-box;
  color: #ffffff;

  display: flex;
  align-items: center;
  justify-content: center;
  gap: 5px;

  position: fixed;
  bottom: 65px;
  left: 50%;
  transform: translateX(-50%);
  z-index: ${BOTTOM_NAVIGATION_Z_INDEX};
`;
