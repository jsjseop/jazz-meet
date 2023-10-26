import styled from '@emotion/styled';
import SubdirectoryArrowRightIcon from '@mui/icons-material/SubdirectoryArrowRight';

type Props = {
  updateTime: string;
  content: string;
};

export const Answer: React.FC<Props> = ({ updateTime, content }) => {
  return (
    <StyledDiv>
      <Header>
        <SubdirectoryArrowRightIcon />
        <LogoImage src="https://github.com/jsh3418/js-calculator-bonobono/assets/57666791/07da94d4-01b3-4e70-8973-db44780f6d6e" />
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
  background-color: #EBEBEB;
`;

const Header = styled.div`
  display: flex;
  align-items: center;
  gap: 8px;
`;

const LogoImage = styled.img`
  width: 146px;
  height: 27px;
  object-fit: cover;
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
