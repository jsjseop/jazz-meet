import styled from '@emotion/styled';
import { useState } from 'react';
import { postInquiryData } from '~/apis/inquiry';
import { AutoSizingTextArea } from '~/components/AutoSizingTextArea';
import {
  INQUIRY_NICKNAME_MAX_LENGTH,
  INQUIRY_NICKNAME_MIN_LENGTH,
  INQUIRY_PASSWORD_MAX_LENGTH,
  INQUIRY_PASSWORD_MIN_LENGTH,
} from '~/constants/LIMITS';
import { useDeviceTypeStore } from '~/stores/useDeviceTypeStore';
import { InquiryCategories } from '~/types/inquiry.types';
import { validateInputLength } from '~/utils/validation';

type Props = {
  currentCategory: InquiryCategories;
};

export const InquiryEditor: React.FC<Props> = ({ currentCategory }) => {
  const isMobile = useDeviceTypeStore((state) => state.isMobile);

  const [inquiryContent, setInquiryContent] = useState('');

  const onChangeInquiryContent = (value: string) => {
    setInquiryContent(value);
  };

  const onInquirySubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    const formData = new FormData(e.currentTarget);
    const nickname = formData.get(NICKNAME)!.toString();
    const password = formData.get(PASSWORD)!.toString();

    const trimmedContent = inquiryContent.trim();
    const trimmedNickname = nickname.trim();

    if (
      !validateInputLength({
        input: trimmedContent,
        minLength: 1,
        onInvalid: () => alert(`문의내용을 입력해주세요`),
      }) ||
      !validateInputLength({
        input: trimmedNickname,
        minLength: INQUIRY_NICKNAME_MIN_LENGTH,
        maxLength: INQUIRY_NICKNAME_MAX_LENGTH,
        onInvalid: (message) => alert(`닉네임을 ${message}`),
      }) ||
      !validateInputLength({
        input: password,
        minLength: INQUIRY_PASSWORD_MIN_LENGTH,
        maxLength: INQUIRY_PASSWORD_MAX_LENGTH,
        onInvalid: (message) => alert(`비밀번호을 ${message}`),
      })
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
      <StyledInquiryInputSection $isMobile={isMobile}>
        <StyledUserInfoInput $isMobile={isMobile}>
          <input
            name={NICKNAME}
            required
            minLength={INQUIRY_NICKNAME_MIN_LENGTH}
            maxLength={INQUIRY_NICKNAME_MAX_LENGTH}
            type="text"
            placeholder="닉네임"
          />
          <input
            name={PASSWORD}
            required
            minLength={INQUIRY_PASSWORD_MIN_LENGTH}
            maxLength={INQUIRY_PASSWORD_MAX_LENGTH}
            type="password"
            placeholder="비밀번호"
          />
        </StyledUserInfoInput>
        <AutoSizingTextArea
          required
          value={inquiryContent}
          onChange={onChangeInquiryContent}
        />
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

const StyledInquiryInputSection = styled.div<{ $isMobile: boolean }>`
  width: 100%;
  display: flex;
  ${({ $isMobile }) => $isMobile && 'flex-direction: column;'}
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
    min-height: ${({ $isMobile }) => ($isMobile ? '64px' : '80px')};
    padding: ${({ $isMobile }) => ($isMobile ? '8px 12px' : '16px 24px')};
    resize: none;
  }
`;

const StyledUserInfoInput = styled.div<{ $isMobile: boolean }>`
  display: flex;
  ${({ $isMobile }) => !$isMobile && `flex-direction: column;`}
  gap: 8px;

  & input {
    width: ${({ $isMobile }) => ($isMobile ? `50%` : '180px')};
    height: ${({ $isMobile }) => ($isMobile ? `36px` : '44px')};
    padding: ${({ $isMobile }) => ($isMobile ? `8px 12px;` : '8px 24px;')};
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
