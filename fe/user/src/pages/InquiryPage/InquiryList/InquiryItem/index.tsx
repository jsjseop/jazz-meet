import styled from '@emotion/styled';
import { useState } from 'react';
import { getInquiryDetail } from '~/apis/inquiry';
import { useDeviceTypeStore } from '~/stores/useDeviceTypeStore';
import { InquiryDetail } from '~/types/api.types';
import { Inquiry } from '~/types/inquiry.types';
import { Mobile } from './Mobile';
import { PC } from './PC';

type Props = {
  inquiry: Inquiry;
};

export const InquiryItem: React.FC<Props> = ({ inquiry }) => {
  const { isMobile } = useDeviceTypeStore((state) => state.deviceType);
  const [inquiryDetail, setInquiryDetail] = useState<InquiryDetail>();

  const updateInquiryDetail = async () => {
    if (inquiryDetail) {
      return;
    }

    const data = await getInquiryDetail(inquiry.id);

    setInquiryDetail(data);
  };

  return (
    <StyledInquiryItem>
      {isMobile ? (
        <Mobile
          inquiry={inquiry}
          inquiryDetail={inquiryDetail}
          updateInquiryDetail={updateInquiryDetail}
        />
      ) : (
        <PC
          inquiry={inquiry}
          inquiryDetail={inquiryDetail}
          updateInquiryDetail={updateInquiryDetail}
        />
      )}
    </StyledInquiryItem>
  );
};

const StyledInquiryItem = styled.li`
  width: 100%;
  padding: 22px 16px;
  box-sizing: border-box;
  display: flex;
  justify-content: space-around;
  align-items: flex-start;
  gap: 5px;

  & * {
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 20px;
    color: #a0a0a0;
  }

  .inquiry-id {
    width: 48px;
  }

  &:has(details[open]) {
    .inquiry-id {
      color: #ff4d00;
    }
  }
`;
