import styled from '@emotion/styled';
import ClearIcon from '@mui/icons-material/Clear';

type Props = {
  children: React.ReactNode;
};

export const PillShapeButton: React.FC<Props> = ({ children }) => {
  return (
    <StyledButton>
      {children}
      <ClearIcon sx={{ '&:hover, &:active': { cursor: 'pointer', opacity: '0.7' } }} />
    </StyledButton>
  );
};

const StyledButton = styled.button`
  padding: 10px 24px;
  border: 1px solid #e1e1e1;
  border-radius: 30px;
  display: flex;
  align-items: center;
  gap: 6px;
  color: #373737;
  font-size: 16px;
  font-weight: 600;

  &:hover {
    cursor: pointer;
    box-shadow: 0px 2px 4px rgba(0, 0, 0, 0.3);
  }

  &:active {
    opacity: 0.7;
  }
`;
