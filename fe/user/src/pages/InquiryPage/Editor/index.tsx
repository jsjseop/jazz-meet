import styled from '@emotion/styled';
import { useState } from 'react';
import { postInquiryData } from '~/apis/inquiry';
import { AutoSizingTextArea } from '~/components/AutoSizingTextArea';
import { InquiryCategories } from '~/types/inquiry.types';
import { validateContent, validateNickname } from './validation';

type Props = {
  currentCategory: InquiryCategories;
};

export const InquiryEditor: React.FC<Props> = ({ currentCategory }) => {
  const [inquiryContent, setInquiryContent] = useState('');

  const onChangeInquiryContent = (value: string) => {
    setInquiryContent(value);
  };

  const onInquirySubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    const formData = new FormData(e.currentTarget);
    const nickname = formData.get(NICKNAME)?.toString();
    const password = formData.get(PASSWORD)?.toString();

    const trimmedContent = inquiryContent.trim();
    const trimmedNickname = nickname?.trim();

    if (
      !validateContent(trimmedContent) ||
      !validateNickname(trimmedNickname) ||
      !trimmedNickname ||
      !password
    ) {
      return;
    }

    try {
      await postInquiryData({
        category: currentCategory,
        nickname: trimmedNickname,
        password,
        content: trimmedContent,
      });

      alert('정상적으로 문의가 등록되었습니다.');
      setInquiryContent('');
      location.reload();
    } catch {
      alert('문의등록에 실패했습니다. 다시 시도해주세요.');
    }
  };

  return (
    <StyledInquiryEditor onSubmit={onInquirySubmit}>
      <StyledInquiryInputSection>
        <AutoSizingTextArea
          required
          value={inquiryContent}
          onChange={onChangeInquiryContent}
        />
        <StyledUserInfoInput>
          <input
            name={NICKNAME}
            required
            minLength={2}
            maxLength={8}
            type="text"
            placeholder="닉네임"
          />
          <input
            name={PASSWORD}
            required
            minLength={4}
            maxLength={20}
            type="password"
            placeholder="비밀번호"
          />
        </StyledUserInfoInput>
      </StyledInquiryInputSection>
      <StyledSubmit>등록하기</StyledSubmit>
    </StyledInquiryEditor>
  );
};

const NICKNAME = 'nickname';
const PASSWORD = 'password';

const StyledInquiryEditor = styled.form`
  margin-top: 170px;
  width: 100%;
  display: flex;
  flex-direction: column;
  gap: 21px;
`;

const StyledInquiryInputSection = styled.div`
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

const StyledUserInfoInput = styled.div`
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
