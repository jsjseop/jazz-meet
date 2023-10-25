import styled from '@emotion/styled';
import { Inquiry } from './Inquiry';

export type InquiryData = {
  id: number;
  status: '검토중' | '답변완료';
  content: string;
  nickname: string;
  createdAt: string;
};

type Props = {
  inquiries: InquiryData[];
};

export const InquiryList: React.FC<Props> = ({ inquiries }) => {
  return (
    <StyledList>
      {inquiries.map((inquiry) => {
        return (
          <li key={inquiry.id}>
            <Inquiry inquiry={inquiry} />
          </li>
        );
      })}
    </StyledList>
  );
};

const StyledList = styled.ul`
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
