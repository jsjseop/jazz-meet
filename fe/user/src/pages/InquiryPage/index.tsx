import styled from '@emotion/styled';
import { useEffect, useState } from 'react';
import { getInquiryData } from '~/apis/inquiry';
import { PaginationBox } from '~/components/PaginationBox';
import { useDeviceTypeStore } from '~/stores/useDeviceTypeStore';
import { InquiryData, InquiryParams } from '~/types/api.types';
import { InquiryCategories } from '~/types/inquiry.types';
import { Categories } from './Categories';
import { InquiryEditor } from './Editor';
import { Header } from './Header';
import { InquiryList } from './InquiryList';

export const InquiryPage: React.FC = () => {
  const { isMobile } = useDeviceTypeStore((state) => state.deviceType);

  const [inquiryParams, setInquiryParams] = useState<InquiryParams>({
    category: '서비스',
    word: '',
    page: 1,
  });
  const [inquiryData, setInquiryData] = useState<InquiryData>();

  const selectCategory = (category: InquiryCategories) => {
    setInquiryParams((prev) => ({ ...prev, category, page: 1 }));
  };

  const onWordChange = (word: string) => {
    setInquiryParams((prev) => ({ ...prev, word }));
  };

  const onSearchClear = () => {
    setInquiryParams((prev) => ({ ...prev, word: '' }));
  };

  const onPageChange = (_: React.ChangeEvent<unknown>, page: number) => {
    setInquiryParams((prev) => ({ ...prev, page }));
  };

  useEffect(() => {
    (async () => {
      const inquiryData = await getInquiryData(inquiryParams);

      setInquiryData(inquiryData);
    })();
  }, [inquiryParams]);

  return (
    <StyledDiv $isMobile={isMobile}>
      <Header
        hasSearchWord={!!inquiryParams.word}
        onWordChange={onWordChange}
        onSearchClear={onSearchClear}
      />

      <Categories
        currentCategory={inquiryParams.category!}
        selectCategory={selectCategory}
      />
      <InquiryList inquiries={inquiryData?.inquiries} />
      <InquiryEditor currentCategory={inquiryParams.category!} />

      <PaginationBox
        maxPage={inquiryData?.maxPage || 1}
        currentPage={inquiryParams.page!}
        onChange={onPageChange}
      />
    </StyledDiv>
  );
};

const StyledDiv = styled.div<{ $isMobile: boolean }>`
  width: 100%;
  max-width: 1200px;
  margin: 0 auto;
  display: flex;
  flex-direction: column;
  align-items: center;
  ${({ $isMobile }) =>
    $isMobile &&
    `  
    padding: 0 16px;
    box-sizing: border-box;
  `}
`;
