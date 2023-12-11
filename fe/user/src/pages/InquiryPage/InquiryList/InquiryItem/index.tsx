import styled from '@emotion/styled';
import ExpandMoreOutlinedIcon from '@mui/icons-material/ExpandMoreOutlined';
import { useState } from 'react';
import { getInquiryDetail } from '~/apis/inquiry';
import { InquiryDetail } from '~/types/api.types';
import { Inquiry } from '~/types/inquiry.types';
import { getFormattedDateString } from '~/utils/dateUtils';
import { Answer } from './Answer';
import { Delete } from './Delete';

type Props = {
  inquiry: Inquiry;
};

export const InquiryItem: React.FC<Props> = ({
  inquiry: { id, status, content, nickname, createdAt },
}) => {
  const [inquiryDetail, setInquiryDetail] = useState<InquiryDetail>();

  const updateInquiryDetail = async () => {
    if (inquiryDetail) {
      return;
    }

    const data = await getInquiryDetail(id);

    setInquiryDetail(data);
  };

  return (
    <StyledInquiryItem>
      <div className="inquiry-id">{id.toString().padStart(2, '0')}</div>
      <StyledStatus>{status}</StyledStatus>
      <StyledDetails onClick={updateInquiryDetail}>
        <StyledSummary>
          <StyledSummaryContent>{content}</StyledSummaryContent>
          <ExpandMoreOutlinedIcon />
        </StyledSummary>

        {inquiryDetail && (
          <>
            <StyledDetailContent>{inquiryDetail.content}</StyledDetailContent>
            {inquiryDetail.answer?.id && (
              <Answer
                updateTime={inquiryDetail.answer.modifiedAt}
                content={inquiryDetail.answer.content}
              />
            )}
          </>
        )}
      </StyledDetails>
      <StyledNickname>{nickname}</StyledNickname>
      <StyledDate>{getFormattedDateString(createdAt)}</StyledDate>
      <Delete inquiryId={id} />
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

const StyledStatus = styled.div`
  color: #5b5b5b;
  width: 92px;
`;

const StyledDetails = styled.details`
  flex: 1;

  &[open] summary > svg {
    transform: rotate(180deg);
  }
`;

const StyledSummary = styled.summary`
  display: flex;
  justify-content: flex-start;
  align-items: center;
  gap: 6px;
  cursor: pointer;

  &:hover {
    text-decoration: underline;
  }

  > svg {
    transition: transform 0.1s ease-in-out;
  }
`;

const StyledSummaryContent = styled.p`
  font-weight: bold;
  color: #000000;
  display: -webkit-box;
  -webkit-line-clamp: 1;
  -webkit-box-orient: vertical;
  overflow: hidden;
`;

const StyledDetailContent = styled.p`
  display: block;
  margin: 30px auto 8px;
  font-size: 20px;
`;

const StyledNickname = styled.div`
  color: #363636;
  width: 100px;
`;

const StyledDate = styled.div`
  width: 116px;
`;
