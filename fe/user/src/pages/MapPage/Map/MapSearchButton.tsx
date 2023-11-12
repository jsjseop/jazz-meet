import styled from '@emotion/styled';
import RefreshIcon from '@mui/icons-material/Refresh';

type Props = {
  hideMapSearchButton: () => void;
  onCurrentViewSearchClick: () => void;
};

export const MapSearchButton: React.FC<Props> = ({
  hideMapSearchButton,
  onCurrentViewSearchClick,
}) => {
  const onMapSearchButtonClick = () => {
    hideMapSearchButton();
    onCurrentViewSearchClick();
  };

  return (
    <StyledMapSearchButton onClick={onMapSearchButtonClick}>
      <RefreshIcon />
      <span>현 지도에서 검색</span>
    </StyledMapSearchButton>
  );
};

const StyledMapSearchButton = styled.button`
  position: absolute;
  bottom: 8%;
  left: 50%;
  transform: translateX(-50%);
  z-index: 1000;
  padding: 12px 16px;
  border-radius: 50px;
  display: flex;
  align-items: center;
  gap: 9px;
  color: #fff;
  background-color: #0775f4;

  &:hover {
    cursor: pointer;
    background-color: #0a84ff;
  }
`;
