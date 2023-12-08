import styled from '@emotion/styled';
import SubdirectoryArrowRightIcon from '@mui/icons-material/SubdirectoryArrowRight';
import JazzMeet from '~/assets/icons/JazzMeet.svg?react';

type Props = {
  updateTime: string;
  content: string;
};

export const Answer: React.FC<Props> = ({ updateTime, content }) => {
  return (
    <StyledDiv>
      <Header>
        <SubdirectoryArrowRightIcon />
        <JazzMeet fill="#ff4d00" />
        <UpdateTime>{updateTime}</UpdateTime>
      </Header>
      <Description>{content}</Description>
    </StyledDiv>
  );
};

const StyledDiv = styled.div`
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  gap: 8px;
  padding: 24px;
  margin-top: 24px;
  background-color: #ebebeb;
`;

const Header = styled.div`
  display: flex;
  align-items: center;
  gap: 8px;
`;

const UpdateTime = styled.span`
  height: 42px;
  padding: 0 8px;
  font-size: 20px;
  color: #a0a0a0;
`;

const Description = styled.p`
  padding-left: 28px;
`;
