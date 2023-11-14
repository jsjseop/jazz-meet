import styled from '@emotion/styled';
import ErrorOutlineIcon from '@mui/icons-material/ErrorOutline';
import { Inquiry } from '~/types/inquiry.types';
import { InquiryItem } from './InquiryItem';

type Props = {
  inquiries?: Inquiry[];
};

export const InquiryList: React.FC<Props> = ({ inquiries }) => {
  return (
    <>
      {inquiries && inquiries.length > 0 ? (
        <StyledInquiryList>
          {inquiries.map((inquiry) => (
            <InquiryItem key={inquiry.id} inquiry={inquiry} />
          ))}
        </StyledInquiryList>
      ) : (
        <StyledEmptyList>
          <ErrorOutlineIcon />
          <span>문의사항이 없습니다.</span>
        </StyledEmptyList>
      )}
    </>
  );
};

const StyledInquiryList = styled.ul`
  width: 100%;
  display: flex;
  flex-direction: column;

  & > li:first-of-type {
    border-top: 1px solid #c7c7c7;
  }

  & > li {
    border-bottom: 1px solid #c7c7c7;
  }
`;

const StyledEmptyList = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 4px;
  height: 100px;
  font-size: 16px;
  color: #888;
  margin-top: 20px;
`;
