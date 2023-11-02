import styled from '@emotion/styled';
import { useState } from 'react';
import { AutoSizingTextArea } from '~/components/AutoSizingTextArea';

export const InquiryEditor: React.FC = () => {
  const [inquiryContent, setInquiryContent] = useState('');

  const onChangeInquiryContent = (value: string) => {
    setInquiryContent(value);
  };

  return (
    <StyledWrapper>
      <StyledForms>
        <AutoSizingTextArea
          value={inquiryContent}
          onChange={onChangeInquiryContent}
        />
        <StyledInputContainer>
          <input type="text" placeholder="닉네임" />
          <input type="password" placeholder="비밀번호" />
        </StyledInputContainer>
      </StyledForms>
      <StyledSubmit>등록하기</StyledSubmit>
    </StyledWrapper>
  );
};

const StyledWrapper = styled.div`
  margin-top: 170px;
  width: 100%;
  display: flex;
  flex-direction: column;
  gap: 21px;
`;

const StyledForms = styled.div`
  width: 100%;
  display: flex;
  gap: 8px;

  & textarea,
  & input {
    box-sizing: border-box;
    border: none;
    font-size: 20px;
    background-color: #ebebeb;

    &::placeholder {
      color: #aeaeae;
    }

    &:focus {
      outline: none;
    }
  }

  & textarea {
    flex: 1;
    min-height: 96px;
    padding: 16px 24px;
    resize: none;
  }
`;

const StyledInputContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 8px;

  & input {
    width: 180px;
    height: 44px;
    padding: 8px 24px;
    border-radius: 2px;

    &::placeholder {
      text-align: center;
    }
  }
`;

const StyledSubmit = styled.button`
  width: 100%;
  height: 48px;
  font-size: 22px;
  font-weight: bold;
  color: #ffffff;
  background-color: #000000;

  &:hover {
    opacity: 0.8;
  }

  &:active {
    opacity: 0.7;
  }
`;
