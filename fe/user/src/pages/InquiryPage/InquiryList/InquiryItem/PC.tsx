import styled from '@emotion/styled';
import ExpandMoreOutlinedIcon from '@mui/icons-material/ExpandMoreOutlined';
import { InquiryDetail } from '~/types/api.types';
import { Inquiry } from '~/types/inquiry.types';
import { getFormattedDateString } from '~/utils/dateUtils';
import { Answer } from './Answer';
import { Delete } from './Delete';

type Props = {
  inquiry: Inquiry;
  inquiryDetail: InquiryDetail | undefined;
  updateInquiryDetail: () => void;
};

export const PC: React.FC<Props> = ({
  inquiry,
  inquiryDetail,
  updateInquiryDetail,
}) => {
  const { id, content, status, nickname, createdAt } = inquiry;
  return (
    <>
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
      <>
        <StyledNickname>{nickname}</StyledNickname>
        <StyledDate>{getFormattedDateString(createdAt)}</StyledDate>
        <Delete inquiryId={id} />
      </>
    </>
  );
};

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
