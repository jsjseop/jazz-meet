import styled from '@emotion/styled';
import { Tabs } from '../Tabs';
import { Tab } from '../Tabs/Tab';
import LocalCafeOutlinedIcon from '@mui/icons-material/LocalCafeOutlined';
import { ShowInfo } from './ShowInfo';

export const RestInfo: React.FC = () => {
  return (
    <StyledRestInfo>
      <Tabs>
        <Tab isSelected>공연정보</Tab>
        <Tab>판매정보</Tab>
        <Tab>공연장정보</Tab>
      </Tabs>

      <StyledRestInfoContent>
        <ShowInfo />
      </StyledRestInfoContent>

      <Tabs>
        <Tab isSelected>공연장정보</Tab>
      </Tabs>
      <StyledContentContainer>
        <StyledContent>
          <LocalCafeOutlinedIcon
            sx={{ width: '28px', height: '28px', fill: '#848484' }}
          />
          <StyledBasicInfoText>
            올댓재즈는 1976년부터 한국의 재즈씬을 이끌어온 한국의 대표브랜드로서
            재즈 매니아들의 성지와도 같은 공간입니다.우리는 재즈의 불모지로
            불리는 한국에서 재즈 장르의 음악이 조금 더 대중들...
          </StyledBasicInfoText>
        </StyledContent>
      </StyledContentContainer>
    </StyledRestInfo>
  );
};

const StyledRestInfo = styled.div`
  padding: 30px 0;
`;

const StyledRestInfoContent = styled.div`
  padding: 22px 54px;
  display: flex;
  gap: 11px;

  @media screen and (max-width: 1500px) {
    flex-direction: column;
  }
`;

const StyledContentContainer = styled.div`
  padding: 24px 30px;
  display: flex;
  flex-direction: column;
`;

const StyledContent = styled.div`
  display: flex;
  gap: 19px;
  align-items: center;

  > svg {
    align-self: flex-start;
  }
`;

const StyledBasicInfoText = styled.div`
  font-size: 20px;
  line-height: 150%;
`;
