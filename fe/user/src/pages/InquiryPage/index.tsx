import { PaginationBox } from '@components/PaginationBox';
import styled from '@emotion/styled';
import React, { useMemo, useState } from 'react';
import { InquiryEditor } from './Editor';
import { InquiryPageHeader } from './Header';
import { InquiryData, InquiryList } from './InquiryList';
import { InquiryTab, TypeFilter } from './TabList';

export const InquiryPage: React.FC = () => {
  const maxPage = useMemo(() => 25, []);
  const [pageNumber, setPageNumber] = useState(
    Math.floor(Math.random() * maxPage),
  );
  const [inquiryTab, setInquiryTab] = useState<InquiryTab>({
    id: 1,
    name: '공연정보',
  });

  const inquires = {
    공연정보: [
      {
        id: 4,
        status: '검토중',
        content: '선예매 진행 시 본인인증 된 아이디 확인은 어떻게 하나요?',
        nickname: '시오',
        createdAt: '2023.10.24',
      },
      {
        id: 3,
        status: '검토중',
        content: '예매한 티켓은 언제 배송되나요?',
        nickname: '지안',
        createdAt: '2023.10.24',
      },
      {
        id: 2,
        status: '답변완료',
        content: '공연 예매가 가능한 결제수단은 어떤 것들이 있나요?',
        nickname: '젤로',
        createdAt: '2023.10.24',
      },
      {
        id: 1,
        status: '답변완료',
        content: '티켓을 이미 배송받았는데 취소하고 싶어요!',
        nickname: '이안',
        createdAt: '2023.10.24',
      },
    ],
    등록문의: [
      {
        id: 5,
        status: '검토중',
        content:
          '등록하기 위한 절차가 어떻게 되나요? 공연장을 등록하고 싶습니다!',
        nickname: '쿤디',
        createdAt: '2023.10.24',
      },
    ],
    기타문의: [] as InquiryData[],
  };

  const handlePageChange = (
    event: React.ChangeEvent<unknown>,
    value: number,
  ) => {
    setPageNumber(value);
  };

  const handleTypeChange = (inquiryTab: InquiryTab) => {
    setInquiryTab(inquiryTab);
  };

  return (
    <StyledDiv>
      <InquiryPageHeader />

      <TypeFilter activaTabId={inquiryTab.id} onChange={handleTypeChange} />
      <InquiryList inquiries={inquires[inquiryTab.name] as InquiryData[]} />
      <InquiryEditor />

      <PaginationBox
        maxPage={maxPage}
        currentPage={pageNumber}
        onChange={handlePageChange}
      />
    </StyledDiv>
  );
};

const StyledDiv = styled.div`
  width: 100%;
  max-width: 1200px;
  margin: 0 auto;
  display: flex;
  flex-direction: column;
  align-items: center;
`;
