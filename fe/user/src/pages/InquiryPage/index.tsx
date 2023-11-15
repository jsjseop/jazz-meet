import styled from '@emotion/styled';
import { useEffect, useState } from 'react';
import { getInquiryData } from '~/apis/inquiry';
import { PaginationBox } from '~/components/PaginationBox';
import { GetInquiryParams, InquiryData } from '~/types/api.types';
import { InquiryCategories } from '~/types/inquiry.types';
import { Categories } from './Categories';
import { InquiryEditor } from './Editor';
import { Header } from './Header';
import { InquiryList } from './InquiryList';

export const InquiryPage: React.FC = () => {
  const [inquiryParams, setInquiryParams] = useState<GetInquiryParams>({
    category: '서비스',
    page: 1,
  });
  const [inquiryData, setInquiryData] = useState<InquiryData>();

  const selectCategory = (category: InquiryCategories) => {
    setInquiryParams((prev) => ({ ...prev, category, page: 1 }));
  };

  const onWordChange = (word: string) => {
    setInquiryParams((prev) => ({ ...prev, word }));
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
    <StyledDiv>
      <Header onWordChange={onWordChange} />

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

const StyledDiv = styled.div`
  width: 100%;
  max-width: 1200px;
  margin: 0 auto;
  display: flex;
  flex-direction: column;
  align-items: center;
`;
