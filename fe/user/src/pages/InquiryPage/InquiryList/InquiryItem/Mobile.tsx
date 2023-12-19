import styled from '@emotion/styled';
import ExpandMoreOutlinedIcon from '@mui/icons-material/ExpandMoreOutlined';
import { InquiryDetail } from '~/types/api.types';
import { Inquiry } from '~/types/inquiry.types';
import { Answer } from './Answer';

type Props = {
  inquiry: Inquiry;
  inquiryDetail: InquiryDetail | undefined;
  updateInquiryDetail: () => void;
};

export const Mobile: React.FC<Props> = ({
  inquiry,
  inquiryDetail,
  updateInquiryDetail,
}) => {
  const { id, status, content } = inquiry;
  return (
    <>
      <StyledDetails onClick={updateInquiryDetail}>
        <StyledSummary>
          <div>
            <div className="inquiry-id">{id.toString().padStart(2, '0')}</div>
            <StyledStatus>{status}</StyledStatus>
          </div>

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
