import styled from '@emotion/styled';
import { Calendar } from './Calendar';
import { Tabs } from './Tabs';
import { Tab } from './Tabs/Tab';

export const RestInfo: React.FC = () => {
  return (
    <StyledRestInfo>
      <Tabs>
        <Tab isSelected>공연정보</Tab>
        <Tab>판매정보</Tab>
        <Tab>공연장정보</Tab>
      </Tabs>

      <StyledRestInfoContent>
        <Calendar />
      </StyledRestInfoContent>
    </StyledRestInfo>
  );
};

const StyledRestInfo = styled.div`
  padding: 30px 0;
`;

const StyledRestInfoContent = styled.div`
  padding: 22px 54px;
`;
