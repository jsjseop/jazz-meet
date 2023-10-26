import styled from '@emotion/styled';
import DeleteIcon from '@mui/icons-material/Delete';
import ExpandMoreOutlinedIcon from '@mui/icons-material/ExpandMoreOutlined';
import { Answer } from './Answer';
import { InquiryData } from '..';

type Props = {
  inquiry: InquiryData;
};

export const Inquiry: React.FC<Props> = ({
  inquiry: { id, status, content, nickname, createdAt },
}) => {
  const textAnswer =
    '안녕하세요 이안님!\n\n 본 상품은 입장일 기준으로 취소 수수료가 발생할 수 있으며, 상품별 취소 요청 시점별 수수료 기준이 상이합니다. 취소를 원하신다면 필히 판매처 혹은 고객센터(1566-5705)로 문의해주세요.';

  return (
    <StyledDiv>
      <div>{id}</div>
      <Status>{status}</Status>
      <Content>
        <Summary>
          <SummaryContent>{content}</SummaryContent>
          <ExpandMoreOutlinedIcon />
        </Summary>

        <DetailContent>{content}</DetailContent>
        <Answer updateTime="2023.10.24 19:32" content={textAnswer} />
      </Content>
      <Nickname>{nickname}</Nickname>
      <div>{createdAt}</div>
      <DeleteIcon sx={{ fontSize: '24px' }} />
    </StyledDiv>
  );
};

const StyledDiv = styled.div`
  box-sizing: border-box;
  width: 100%;
  display: flex;
  justify-content: space-around;
  align-items: flex-start;
  padding: 22px 16px;
  gap: 20px;

  & * {
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 20px;
    color: #a0a0a0;
  }

  & > svg:hover {
    fill: #000000;
    cursor: pointer;
  }

  &:has(details[open]) {
    & > div:first-of-type {
      color: #ff4d00;
    }
  }
`;

const Status = styled.div`
  color: #5b5b5b;
  width: 80px;
`;

const Content = styled.details`
  flex: 1;

  & summary > svg {
    transition: transform 0.1s ease-in-out;
  }

  &[open] summary > svg {
    transform: rotate(180deg);
  }
`;

const Summary = styled.summary`
  display: flex;
  justify-content: flex-start;
  align-items: center;
  gap: 6px;

  &:hover,
  & > svg:hover {
    cursor: pointer;
    text-decoration: underline;
  }
`;

const SummaryContent = styled.p`
  font-weight: bold;
  color: #000000;
  display: -webkit-box;
  -webkit-line-clamp: 1;
  -webkit-box-orient: vertical;
  overflow: hidden;
`;

const DetailContent = styled.p`
  display: block;
  margin: 30px auto 8px;
  font-size: 20px;
`;

const Nickname = styled.div`
  color: #363636;
`;
