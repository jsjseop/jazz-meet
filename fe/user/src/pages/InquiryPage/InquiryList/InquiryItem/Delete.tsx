import styled from '@emotion/styled';
import CloseIcon from '@mui/icons-material/Close';
import DeleteIcon from '@mui/icons-material/Delete';
import { useState } from 'react';
import { deleteInquiry } from '~/apis/inquiry';

type Props = {
  inquiryId: number;
};

export const Delete: React.FC<Props> = ({ inquiryId }) => {
  const [isPasswordInputOpen, setIsPasswordInputOpen] = useState(false);
  const openPasswordInput = () => setIsPasswordInputOpen(true);
  const closePasswordInput = () => setIsPasswordInputOpen(false);

  const onDeleteInquirySubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    const password = new FormData(e.currentTarget).get(PASSWORD) as string;
    const response = await deleteInquiry(inquiryId, password);

    if (response.errorMessage) {
      alert(response.errorMessage);

      return;
    }

    alert('정상적으로 삭제되었습니다.');
    closePasswordInput();
  };

  return (
    <StyledDelete>
      <DeleteIcon sx={{ fontSize: '24px' }} onClick={openPasswordInput} />
      {isPasswordInputOpen && (
        <StyledPasswordContainer onSubmit={onDeleteInquirySubmit}>
          <StyledPasswordInput
            required
            name={PASSWORD}
            type="password"
            placeholder="비밀번호"
          />
          <StyledSubmitButton>확인</StyledSubmitButton>
          <StyledCloseButton onClick={closePasswordInput}>
            <CloseIcon />
          </StyledCloseButton>
        </StyledPasswordContainer>
      )}
    </StyledDelete>
  );
};

const PASSWORD = 'password';

const StyledDelete = styled.div`
  width: 40px;
  position: relative;
  align-self: start;

  > svg {
    cursor: pointer;

    &:hover {
      fill: #000000;
    }
  }
`;

const StyledPasswordContainer = styled.form`
  height: 100%;
  padding: 1px 3px;
  background-color: #141313;
  border: 1px solid #47484e;
  position: absolute;
  top: 100%;
  right: 0;
  display: flex;
  gap: 4px;
`;

const StyledPasswordInput = styled.input`
  font-size: 16px;
  border: none;
  outline: none;
  color: #000;
`;

const StyledSubmitButton = styled.button`
  height: 100%;
  padding: 5px;
  font-size: 12px;
  color: #fff;
  background-color: #1b1b1b;
  cursor: pointer;
  white-space: nowrap;
`;

const StyledCloseButton = styled.button`
  padding: 2px;
  cursor: pointer;
  display: flex;
  justify-content: center;
  align-items: center;
`;
